package mixture.hutech.backend.controller;

import mixture.hutech.backend.dto.request.AuthenticationRequest;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private AuthenticationRequest authenticationRequest;
    private AuthenticationResponse successResponse;

    @BeforeEach
    public void setUp() {
        authenticationRequest = AuthenticationRequest.builder()
                .email("nguyenvana@gmail.com")
                .password("nguyenvana123")
                .build();

        successResponse = AuthenticationResponse.builder()
                .name("Nguyen Van A")
                .role("USER")
                .email("nguyenvana@gmail.com")
                .accessToken("jwt-token")
                .refreshToken("refresh-token")
                .errorCode(ErrorCodeEnum.OK)
                .message("Login Successful")
                .build();
    }

    @Test
    void testLogin_Success() {
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(successResponse);

        ResponseEntity<AuthenticationResponse> response =
                authenticationController.authenticate(authenticationRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(successResponse, response.getBody());
        assertEquals("Login Successful", response.getBody().getMessage());
        assertEquals(ErrorCodeEnum.OK, response.getBody().getErrorCode());
        assertEquals("jwt-token", response.getBody().getAccessToken());
        assertEquals("refresh-token", response.getBody().getRefreshToken());
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    void testLogin_UserNotFound() {
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new ApiException(ErrorCodeEnum.USER_NOT_FOUND));

        ResponseEntity<AuthenticationResponse> response =
                authenticationController.authenticate(authenticationRequest);

        assertErrorResponse(response, ErrorCodeEnum.USER_NOT_FOUND);
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    void testLogin_InvalidCredentials() {
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new ApiException(ErrorCodeEnum.INVALID_CREDENTIALS));

        ResponseEntity<AuthenticationResponse> response =
                authenticationController.authenticate(authenticationRequest);

        assertErrorResponse(response, ErrorCodeEnum.INVALID_CREDENTIALS);
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    void testLogin_AccountNotActivated() {
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new ApiException(ErrorCodeEnum.ACCOUNT_NOT_ACTIVATED));

        ResponseEntity<AuthenticationResponse> response =
                authenticationController.authenticate(authenticationRequest);

        assertErrorResponse(response, ErrorCodeEnum.ACCOUNT_NOT_ACTIVATED);
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }

    @Test
    void testLogin_ServerError() {
        when(authenticationService.authenticate(any(AuthenticationRequest.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<AuthenticationResponse> response =
                authenticationController.authenticate(authenticationRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorCodeEnum.AUTHENTICATION_FAILED, response.getBody().getErrorCode());
        assertEquals(ErrorCodeEnum.AUTHENTICATION_FAILED.getMessage(),
                response.getBody().getMessage());
        verify(authenticationService, times(1)).authenticate(authenticationRequest);
    }
}