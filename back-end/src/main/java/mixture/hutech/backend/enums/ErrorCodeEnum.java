package mixture.hutech.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorCodeEnum {
    OK(200,"Success", HttpStatus.OK),
    EMAIL_ALREADY_EXISTS(409, "Email đã tồn tại", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS(409,"Số điện thoại đã tồn tại", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(401, "Thông tin đăng nhập không hợp lệ", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(404, "Tài khoản chưa được đăng ký", HttpStatus.NOT_FOUND),
    REGISTRATION_FAILED(400, "Đăng ký thất bại", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(401, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(404,"Token không hợp lệ", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVATED(401,"Tài khoản chưa được kích hoạt", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_FAILED(401, "Xác thực thất bại", HttpStatus.UNAUTHORIZED),
    DOCTOR_SCHEDULE_NOT_FOUND(404, "Không tìm thấy lịch làm việc của bác sĩ", HttpStatus.NOT_FOUND),
    DOCTOR_NOT_FOUND(404,"Không tìm thấy bác sĩ", HttpStatus.NOT_FOUND),
    SLOT_NOT_AVAILABLE(400,"Lịch đã được đặt trước", HttpStatus.BAD_REQUEST),
    INVALID_APPOINTMENT_TIME(400, "Thời gian bắt đầu phải trước thời gian kết thúc", HttpStatus.BAD_REQUEST),
    INVALID_BOOKING_DATE(400, "Ngày đặt phải trước ngày hẹn", HttpStatus.BAD_REQUEST),
    FULL_SLOT(400,"Khung giờ đã được đặt trước", HttpStatus.BAD_REQUEST),
    APPOINTMENT_CANCELLED(404,"Không tìm thấy lịch hẹn bị huỷ", HttpStatus.NOT_FOUND),
    APPOINTMENT_ALREADY_CANCELED(400,"Cuộc hẹn đã bị huỷ", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_PAST_APPOINTMENT(400, "Không thể huỷ cuộc hẹn đã hoặc đang diễn ra", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(404,"Không tìm thấy lịch hẹn", HttpStatus.NOT_FOUND),
    SPECIALIZATION_NOT_FOUND(404,"Không tìm thấy chuyên khoa", HttpStatus.NOT_FOUND),
    TOKEN_ALREADY_ACTIVATED(400,"Tài khoản đã được kích hoạt" ,HttpStatus.BAD_REQUEST ),
    TOKEN_NOT_FOUND(404,"Không tìm thấy token hợp lệ" , HttpStatus.NOT_FOUND ),
    VERIFICATION_FAILED(401,"Xác thực thất bại",HttpStatus.UNAUTHORIZED ),
    INVALID_CURRENT_PASSWORD(401,"Mật khẩu hiện tại không đúng", HttpStatus.UNAUTHORIZED ),
    DISEASE_NOT_FOUND(404,"Không tìm thấy bệnh", HttpStatus.NOT_FOUND),
    SYMPTOM_NOT_FOUND(404, "Không tìm thấy triệu chứng", HttpStatus.NOT_FOUND),
    EXTERNAL_SERVICE_ERROR(500, "Lỗi kết nối dịch vụ ngoại vi", HttpStatus.INTERNAL_SERVER_ERROR),
    CANNOT_CANCEL_WITHIN_24_HOURS(400, "Không thể huỷ cuộc hẹn trong vòng 24 giờ", HttpStatus.BAD_REQUEST),
    CANNOT_RESCHEDULE_WITHIN_12_HOURS(400, "Không thể đổi lịch trong vòng 12 giờ", HttpStatus.BAD_REQUEST),
    INVALID_CLINIC_STATUS(400, "Chuyển đổi trạng thái không hợp lệ", HttpStatus.BAD_REQUEST),
    APPOINTMENT_ALREADY_NO_SHOW(400, "Cuộc hẹn đã được đánh dấu là không đến", HttpStatus.BAD_REQUEST),
    APPOINTMENT_ALREADY_COMPLETED(400, "Cuộc hẹn đã được đánh dấu là đã hoàn thành", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_HAPPENED(400, "Cuộc hẹn chưa diễn ra", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FINISHED(400, "Cuộc hẹn chưa hoàn thành", HttpStatus.BAD_REQUEST),
    SEARCH_CRITERIA_REQUIRED(400, "Cần có ít nhất một tiêu chí tìm kiếm", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
