package mixture.hutech.backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.entity.Appointment;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import mixture.hutech.backend.repository.AppointmentRepository;
import mixture.hutech.backend.service.EmailService;
import mixture.hutech.backend.service.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;

    @Override
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void sendAppointmentReminder() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Appointment> upcomingAppointments = appointmentRepository.findByAppointmentTakenDateAndAppointmentStatus(
                tomorrow,
                AppointmentStatusEnum.CONFIRMED
        );

        for (Appointment appointment : upcomingAppointments) {
            // Check if reminder hasn't been sent already
            if (Boolean.FALSE.equals(appointment.getReminderSent())) {
                try {
                    sendReminderEmail(appointment);
                    appointment.setReminderSent(true);
                    appointmentRepository.save(appointment);
                } catch (Exception e) {
                    // Log the error or handle it appropriately
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendReminderEmail(Appointment appointment) throws MessagingException {
        String recipientEmail = appointment.getUser().getEmail();
        String patientName = appointment.getUser().getUsername();
        String doctorName = appointment.getDoctorSchedule().getUser().getUsername();
        String appointmentDate = appointment.getAppointmentTakenDate().toString();
        String appointmentTime = appointment.getProbableStartTime().toString() + " - " +
                appointment.getActualEndTime().toString();

        emailService.sendMailAppointmentReminder(
                recipientEmail,
                patientName,
                doctorName,
                appointmentDate,
                appointmentTime
        );
    }
}
