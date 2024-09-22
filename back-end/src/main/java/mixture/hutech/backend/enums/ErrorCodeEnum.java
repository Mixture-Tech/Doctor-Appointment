package mixture.hutech.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {
    OK(200,"", HttpStatus.OK),
    EMAIL_ALREADY_EXISTS(409, "Email đã tồn tại", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(401, "Thông tin đăng nhập không hợp lệ", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(404, "Tài khoản chưa được đăng ký", HttpStatus.NOT_FOUND),
    REGISTRATION_FAILED(400, "Đăng ký thất bại", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(401, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(404,"Token không hợp lệ", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVATED(401,"Tài khoản chưa được kích hoạt", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED(401, "Xác thực thất bại", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
