package mixture.hutech.backend.service.impl;

import jakarta.mail.MessagingException;
import mixture.hutech.backend.dto.request.RegisterRequest;
import mixture.hutech.backend.dto.response.AuthenticationResponse;
import mixture.hutech.backend.entity.PasswordResetToken;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceImplTest {
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

    private RegisterRequest registerRequest;
    private Role defaultRole;

    @BeforeEach
    public void setUp() {
        registerRequest = RegisterRequest.builder()
                .username("Nguyen Van A")
                .email("nguyenvana@gmail.com")
                .phone("0123456789")
                .password("nguyenvana123")
                .role(1)
                .build();

        defaultRole = new Role();
        defaultRole.setId(1);
        defaultRole.setName("USER");
    }

    @Test
    void testRegister_Success() throws MessagingException {
        // Mock các hành vi
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(registerRequest.getPhone())).thenReturn(Optional.empty());
        when(roleRepository.findById(registerRequest.getRole())).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordResetTokenRepository.save(any(PasswordResetToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Gọi phương thức register
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Kiểm tra kết quả với các kiểu assert đa dạng
        assertEquals(ErrorCodeEnum.OK, response.getErrorCode(), "Error code should be OK");
        assertNotEquals(ErrorCodeEnum.EMAIL_ALREADY_EXISTS, response.getErrorCode(), "Error code should not be EMAIL_ALREADY_EXISTS");
        assertEquals("Registration Successful", response.getMessage());
        assertNotNull(response, "Response should not be null");
        assertNull(response.getAccessToken(), "Access token should be null");
        assertNull(response.getRefreshToken(), "Refresh token should be null");
//        assertTrue(response.getErrorCode() == ErrorCodeEnum.OK, "Error code should be OK");
//        assertFalse(response.getAccessToken() == null, "Access token should not be present");

        // Verify các phương thức được gọi
        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(userRepository, times(1)).findByPhone(registerRequest.getPhone());
        verify(roleRepository, times(1)).findById(registerRequest.getRole());
        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordResetTokenRepository, times(1)).save(any(PasswordResetToken.class));
        verify(emailService, times(1)).sendMailWithTokenRegister(anyString(), anyString(), anyString());
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Mock email đã tồn tại
        User existingUser = new User();
        existingUser.setEmail(registerRequest.getEmail());
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(existingUser));

        // Kiểm tra exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.register(registerRequest);
        });

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.EMAIL_ALREADY_EXISTS, exception.getErrorCodeEnum(), "Error code should be EMAIL_ALREADY_EXISTS");
        assertNotEquals(ErrorCodeEnum.PHONE_ALREADY_EXISTS, exception.getErrorCodeEnum(), "Should not be PHONE_ALREADY_EXISTS");
        assertNotNull(exception.getMessage(), "Exception message should not be null");
        assertTrue(exception.getErrorCodeEnum().getHttpStatus().is4xxClientError(), "Should be a 4xx error");
        assertFalse(exception.getErrorCodeEnum() == ErrorCodeEnum.OK, "Error code should not be OK");

        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(userRepository, never()).findByPhone(anyString());
    }

    @Test
    void testRegister_PhoneAlreadyExists() {
        // Mock phone đã tồn tại
        User existingUser = new User();
        existingUser.setPhone(registerRequest.getPhone());
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(registerRequest.getPhone())).thenReturn(Optional.of(existingUser));

        // Kiểm tra exception
        ApiException exception = assertThrows(ApiException.class, () -> {
            authenticationService.register(registerRequest);
        });

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.PHONE_ALREADY_EXISTS, exception.getErrorCodeEnum());
        assertNotEquals(ErrorCodeEnum.EMAIL_ALREADY_EXISTS, exception.getErrorCodeEnum());
        assertNotNull(exception, "Exception should not be null");
        assertTrue(exception.getErrorCodeEnum() != ErrorCodeEnum.OK, "Error code should indicate failure");
        assertFalse(exception.getErrorCodeEnum().getMessage().isEmpty(), "Error message should not be empty");

        verify(userRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(userRepository, times(1)).findByPhone(registerRequest.getPhone());
    }

    @Test
    void testRegister_RoleNotFound_UseDefault() {
        // Mock role không tìm thấy, trả về role mặc định
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(registerRequest.getPhone())).thenReturn(Optional.empty());
        when(roleRepository.findById(registerRequest.getRole())).thenReturn(Optional.empty());
        when(roleRepository.findFirstByOrderByIdAsc()).thenReturn(defaultRole);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordResetTokenRepository.save(any(PasswordResetToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Gọi phương thức
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.OK, response.getErrorCode());
        assertNotEquals("Registration Failed", response.getMessage(), "Message should indicate success");
        assertNotNull(response.getMessage(), "Message should not be null");
        assertTrue(response.getMessage().contains("Successful"), "Message should contain 'Successful'");
        assertFalse(response.getErrorCode().getHttpStatus().is5xxServerError(), "Should not be a server error");

        verify(roleRepository, times(1)).findFirstByOrderByIdAsc();
    }

    @Test
    void testRegister_EmailServiceFails() throws MessagingException {
        // Mock các hành vi thành công nhưng email service thất bại
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhone(registerRequest.getPhone())).thenReturn(Optional.empty());
        when(roleRepository.findById(registerRequest.getRole())).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordResetTokenRepository.save(any(PasswordResetToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        doThrow(new RuntimeException("Email error")).when(emailService)
                .sendMailWithTokenRegister(anyString(), anyString(), anyString());

        // Gọi phương thức
        AuthenticationResponse response = authenticationService.register(registerRequest);

        // Kiểm tra với các kiểu assert
        assertEquals(ErrorCodeEnum.OK, response.getErrorCode());
        assertNotEquals(ErrorCodeEnum.EMAIL_ALREADY_EXISTS, response.getErrorCode());
        assertNotNull(response, "Response should not be null");
        assertTrue(response.getErrorCode().getHttpStatus().is2xxSuccessful(), "Should be a successful status");
        assertFalse(response.getMessage().contains("Failed"), "Message should not indicate failure");

        verify(emailService, times(1)).sendMailWithTokenRegister(anyString(), anyString(), anyString());
    }
}
