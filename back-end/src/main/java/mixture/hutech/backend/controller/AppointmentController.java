package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/appointments")
@RequiredArgsConstructor
@Validated
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/{doctorId}")
    public ResponseEntity<AppointmentResponse> createAppointment(
            @PathVariable String doctorId,
            @RequestBody AppointmentRequest appointmentRequest,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            String currentUserEmail = userDetails.getUsername();
            appointmentRequest.setDoctor(doctorId);

            AppointmentResponse appointmentResponse = appointmentService.createAppointment(appointmentRequest, currentUserEmail);
            return ResponseEntity
                    .status(appointmentResponse.getErrorCode().getHttpStatus())
                    .body(appointmentResponse);
        } catch (ApiException e) {
            return ResponseEntity
                    .status(e.getErrorCodeEnum().getHttpStatus())
                    .body(AppointmentResponse.builder()
                            .errorCode(e.getErrorCodeEnum())
                            .message(e.getMessage())
                            .build());
        }
    }
}