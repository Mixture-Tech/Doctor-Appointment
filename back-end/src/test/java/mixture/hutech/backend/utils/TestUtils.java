package mixture.hutech.backend.utils;

import mixture.hutech.backend.dto.response.AuthenticationResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static void assertErrorResponse(ResponseEntity<AuthenticationResponse> response,
                                           ErrorCodeEnum errorCode) {
        assertEquals(errorCode.getHttpStatus(), response.getStatusCode());
        assertEquals(errorCode, response.getBody().getErrorCode());
        assertEquals(errorCode.getMessage(), response.getBody().getMessage());
    }
}