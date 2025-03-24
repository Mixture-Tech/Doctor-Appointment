package mixture.hutech.backend.controller;

import mixture.hutech.backend.dto.request.RegisterRequest;
import mixture.hutech.backend.dto.response.AuthenticationResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static mixture.hutech.backend.utils.TestUtils.assertErrorResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private RegisterRequest registerRequest;
    private AuthenticationResponse successResponse;

    @BeforeEach
    public void setUp() {
        registerRequest = RegisterRequest.builder()
                .username("Nguyen Van A")
                .email("nguyenvana@gmail.com")
                .phone("0123456789")
                .password("nguyenvana123")
                .role(1)
                .build();

        successResponse = AuthenticationResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Registration Successful")
                .accessToken(null)
                .refreshToken(null)
                .build();
    }

    @Test
    void testRegister_Success() {
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(successResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successResponse, response.getBody());
        assertEquals("Registration Successful", response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.OK, response.getBody().getErrorCode());
        assertNull(response.getBody().getAccessToken());
        assertNull(response.getBody().getRefreshToken());
        verify(authenticationService, times(1)).register(registerRequest);
    }

    @Test
    void testRegister_PhoneAlreadyExists() {
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new ApiException(ErrorCodeEnum.PHONE_ALREADY_EXISTS));

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertErrorResponse(response, ErrorCodeEnum.PHONE_ALREADY_EXISTS);
        verify(authenticationService, times(1)).register(registerRequest);
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new ApiException(ErrorCodeEnum.EMAIL_ALREADY_EXISTS));

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertErrorResponse(response, ErrorCodeEnum.EMAIL_ALREADY_EXISTS);
        verify(authenticationService, times(1)).register(registerRequest);
    }

    @Test
    void testRegister_RegistrationFailed() {
        when(authenticationService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Error"));

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(registerRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorCodeEnum.REGISTRATION_FAILED, response.getBody().getErrorCode());
        assertEquals(ErrorCodeEnum.REGISTRATION_FAILED.getMessage(), response.getBody().getMessage());
        verify(authenticationService, times(1)).register(registerRequest);
    }
}