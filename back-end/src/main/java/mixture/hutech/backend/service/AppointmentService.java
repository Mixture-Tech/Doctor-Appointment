package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request, String userEmail);
    AppointmentResponse cancelAppointment(String appointmentId, String userEmail);
    List<AppointmentResponse> getAppointmentsByUserId(String userId);
}
