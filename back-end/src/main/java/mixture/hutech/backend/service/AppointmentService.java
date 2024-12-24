package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.request.ClinicRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.enums.ClinicStatusEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request, String userEmail);
    AppointmentResponse cancelAppointment(String appointmentId, String userEmail);
    List<AppointmentResponse> listAppointmentByUser(String userEmail);
    AppointmentResponse updateAppointment(String appointmentId, AppointmentRequest request, String userEmail);
    Page<AppointmentResponse> listAppointmentsPaginated(int page, int size);
    AppointmentResponse updateClinicStatus(String appointmentId, ClinicStatusEnum clinicStatusEnum);
    Page<AppointmentResponse> searchAppointments(String appointmentCode, String username, int page, int size);
}
