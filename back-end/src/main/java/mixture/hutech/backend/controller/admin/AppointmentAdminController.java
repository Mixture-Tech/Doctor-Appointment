package mixture.hutech.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.request.ClinicRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.entity.CustomUserDetail;
import mixture.hutech.backend.enums.ClinicStatusEnum;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/appointments")
@RequiredArgsConstructor
public class AppointmentAdminController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<MessageResponse> listAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Page<AppointmentResponse> appointmentResponse = appointmentService.listAppointmentsPaginated(page, size);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .errorCode(ErrorCodeEnum.OK)
                            .message(ErrorCodeEnum.OK.getMessage())
                            .data(appointmentResponse)
                            .build()
            );
        } catch (ApiException e) {
            return ResponseEntity
                    .status(e.getErrorCodeEnum().getHttpStatus())
                    .body(MessageResponse.builder()
                            .errorCode(e.getErrorCodeEnum())
                            .message(e.getMessage())
                            .build());
        }
    }

    @PostMapping("/{appointmentId}/clinic-status")
    public ResponseEntity<MessageResponse> updateClinicStatus(
            @PathVariable String appointmentId,
            @RequestBody ClinicRequest clinicRequest) {
        try {
            AppointmentResponse appointmentResponse = appointmentService.updateClinicStatus(appointmentId, clinicRequest.getClinicStatusEnum());

            return ResponseEntity.ok(MessageResponse.builder()
                    .errorCode(ErrorCodeEnum.OK)
                    .message(ErrorCodeEnum.OK.getMessage())
                    .data(appointmentResponse)
                    .build());
        } catch (ApiException e) {
            return ResponseEntity
                    .status(e.getErrorCodeEnum().getHttpStatus())
                    .body(MessageResponse.builder()
                            .errorCode(e.getErrorCodeEnum())
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<MessageResponse> searchAppointments(
            @RequestParam(required = false) String appointmentCode,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<AppointmentResponse> appointmentResponse = appointmentService.searchAppointments(appointmentCode, username, page, size);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .errorCode(ErrorCodeEnum.OK)
                            .message(ErrorCodeEnum.OK.getMessage())
                            .data(appointmentResponse)
                            .build()
            );
        } catch (ApiException e) {
            return ResponseEntity
                    .status(e.getErrorCodeEnum().getHttpStatus())
                    .body(MessageResponse.builder()
                            .errorCode(e.getErrorCodeEnum())
                            .message(e.getMessage())
                            .build());
        }
    }
}
