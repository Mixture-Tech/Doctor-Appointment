package mixture.hutech.backend.service.impl;

import mixture.hutech.backend.dto.request.AuthenticationRequest;
import mixture.hutech.backend.dto.response.AuthenticationResponse;
import mixture.hutech.backend.entity.CustomUserDetail;
import mixture.hutech.backend.entity.Role;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.PasswordResetTokenRepository;
import mixture.hutech.backend.repository.RoleRepository;
import mixture.hutech.backend.repository.UserRepository;
import mixture.hutech.backend.service.EmailService;
import mixture.hutech.backend.service.JwtService;
import mixture.hutech.backend.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private AuthenticationRequest loginRequest;
    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        // Khởi tạo request mẫu
        loginRequest = AuthenticationRequest.builder()
                .email("nguyenvana@gmail.com")
                .password("nguyenvana123")
                .build();

        // Khởi tạo user mẫu
        role = new Role();
        role.setId(1);
        role.setName("USER");

        user = User.builder()
                .email("nguyenvana@gmail.com")
                .password("encodedPassword")
                .username("Nguyen Van A")
                .role(role)
                .isActive(true) // Tài khoản đã kích hoạt
                .build();
    }

    @Test
    void testAuthenticate_Success() {
        // Mock các hành vi
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Không cần trả về gì cụ thể, chỉ cần không throw exception
        when(jwtService.generateToken(any(CustomUserDetail.class))).thenReturn("jwtToken");
        when(jwtService.generateRefreshToken(any(CustomUserDetail.class))).thenReturn("refreshToken");

        // Gọi phương thức authenticate
        AuthenticationResponse response = authenticationService.authenticate(loginRequest);

        // Kiểm tra kết quả với các kiểu assert
        assertEquals(ErrorCodeEnum.OK, response.getErrorCode(), "Error code should be OK");
        assertNotEquals(ErrorCodeEnum.INVALID_CREDENTIALS, response.getErrorCode(), "Error code should not be INVALID_CREDENTIALS");
        assertEquals("Login Successful", response.getMessage());
        assertEquals("Nguyen Van A", response.getName());
        assertEquals("USER", response.getRole());
        assertEquals("jwtToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertNotNull(response.getAccessToken(), "Access token should not be null");
        assertNotNull(response.getRefreshToken(), "Refresh token should not be null");
        assertTrue(response.getEmail().equals(loginRequest.getEmail()), "Email should match request");
        assertFalse(response.getMessage().contains("Failed"), "Message should not indicate failure");

        // Verify các phương thức được gọi
        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(CustomUserDetail.class));
        verify(jwtService, times(1)).generateRefreshToken(any(CustomUserDetail.class));
        verify(tokenService, times(1)).revokeAllUserTokens(user);
        verify(tokenService, times(1)).saveUserToken(user, "jwtToken");
    }
    @Test
    void testAuthenticate_UserNotFound() {
        // Mock user không tồn tại
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        // Kiểm tra exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.authenticate(loginRequest);
        });

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.USER_NOT_FOUND, exception.getErrorCodeEnum());
        assertNotEquals(ErrorCodeEnum.INVALID_CREDENTIALS, exception.getErrorCodeEnum());
        assertNotNull(exception.getMessage(), "Exception message should not be null");
        assertTrue(exception.getErrorCodeEnum().getHttpStatus().is4xxClientError(), "Should be a 4xx error");
        assertFalse(exception.getErrorCodeEnum() == ErrorCodeEnum.OK, "Error code should not be OK");

        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(authenticationManager, never()).authenticate(any()); // Không gọi nếu user không tồn tại
    }

    @Test
    void testAuthenticate_AccountNotActivated() {
        // Mock user chưa kích hoạt
        user.setActive(false);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));

        // Kiểm tra exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.authenticate(loginRequest);
        });

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.ACCOUNT_NOT_ACTIVATED, exception.getErrorCodeEnum());
        assertNotEquals(ErrorCodeEnum.USER_NOT_FOUND, exception.getErrorCodeEnum());
        assertNotNull(exception, "Exception should not be null");
        assertTrue(exception.getErrorCodeEnum() != ErrorCodeEnum.OK, "Error code should indicate failure");
        assertFalse(exception.getMessage().isEmpty(), "Error message should not be empty");

        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        // Mock thông tin đăng nhập sai
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Kiểm tra exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.authenticate(loginRequest);
        });

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.INVALID_CREDENTIALS, exception.getErrorCodeEnum());
        assertNotEquals(ErrorCodeEnum.ACCOUNT_NOT_ACTIVATED, exception.getErrorCodeEnum());
        assertNotNull(exception.getErrorCodeEnum(), "Error code should not be null");
        assertTrue(exception.getErrorCodeEnum().getHttpStatus().is4xxClientError(), "Should be a client error");
        assertFalse(exception.getErrorCodeEnum() == ErrorCodeEnum.OK, "Should not be successful");

        verify(userRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
