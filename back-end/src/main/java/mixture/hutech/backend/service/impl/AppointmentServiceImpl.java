package mixture.hutech.backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.entity.Appointment;
import mixture.hutech.backend.entity.DoctorSchedule;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import mixture.hutech.backend.enums.BookingTypeEnum;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.AppointmentRepository;
import mixture.hutech.backend.repository.DoctorScheduleRepository;
import mixture.hutech.backend.repository.UserRepository;
import mixture.hutech.backend.service.AppointmentService;
import mixture.hutech.backend.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private static final int MAX_APPOINTMENTS_PER_SLOT = 3;

    /**
     * Create an appointment.
     */
    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request, String userEmail) {
        User patient = getUserByEmail(userEmail);
        User doctor = getDoctorById(request.getDoctor());
        DoctorSchedule availableSlot = getAvailableSlot(doctor.getId(), request);

        if(availableSlot.getCurrentAppointment() >= MAX_APPOINTMENTS_PER_SLOT){
            throw new ApiException(ErrorCodeEnum.FULL_SLOT);
        }

        Appointment appointment = createAppointmentEntity(request, patient, availableSlot);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        updatePatientInfo(patient, request);
        updateDoctorSchedule(availableSlot, savedAppointment);

        try {
            sendConfirmationEmail(patient, doctor, savedAppointment);
        }catch (MessagingException e){
            System.out.println("Mail error: " + e.getMessage());
        }

        return buildAppointmentResponse(savedAppointment, patient, doctor, "Appointment created successfully");
    }

    /**
     * Cancels an upcoming appointment.
     */
    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(String appointmentId, String userEmail) {
        User patient = getUserByEmail(userEmail);
        Appointment appointment = getAppointmentById(appointmentId);

        if(!appointment.getUser().getId().equals(patient.getId())){
            throw new ApiException(ErrorCodeEnum.AUTHENTICATION_FAILED);
        }

        if(appointment.getAppointmentStatus() == AppointmentStatusEnum.CANCELLED){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_ALREADY_CANCELED);
        }

        if(!isUpcoming(appointment)){
            throw new ApiException(ErrorCodeEnum.CANNOT_CANCEL_PAST_APPOINTMENT);
        }

        appointment.setReminderSent(false);
        appointment.setAppointmentStatus(AppointmentStatusEnum.CANCELLED);
        appointmentRepository.save(appointment);

        DoctorSchedule doctorSchedule = appointment.getDoctorSchedule();
        doctorSchedule.setCurrentAppointment(doctorSchedule.getCurrentAppointment() - 1);
        doctorScheduleRepository.save(doctorSchedule);

        return buildAppointmentResponse(appointment, patient, appointment.getDoctorSchedule().getUser(), "Appointment has been cancelled");
    }

    private void sendConfirmationEmail(User patient, User doctor, Appointment appointment) throws MessagingException {
        emailService.sendMailAppointmentConfirmation(
                patient.getEmail(),
                patient.getUsername(),
                doctor.getUsername(),
                appointment.getAppointmentTakenDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                appointment.getProbableStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - " + appointment.getActualEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
        );
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCodeEnum.USER_NOT_FOUND));
    }

    private User getDoctorById(String doctorId) {
        return userRepository.findDoctorById(doctorId)
                .orElseThrow(() -> new ApiException(ErrorCodeEnum.DOCTOR_SCHEDULE_NOT_FOUND));
    }

    private Appointment getAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FOUND)
        );
    }

    private DoctorSchedule getAvailableSlot(String doctorId, AppointmentRequest request) {
        return doctorScheduleRepository.findAvailableSlotByDoctorAndDayAndHours(
                doctorId,
                request.getAppointmentTakenDate().getDayOfWeek().toString(),
                request.getAppointmentTakenDate(),
                request.getStartTime(),
                request.getEndTime()
        ).filter(slot -> slot.getCurrentAppointment() <= MAX_APPOINTMENTS_PER_SLOT)
        .orElseThrow(() -> new ApiException(ErrorCodeEnum.DOCTOR_SCHEDULE_NOT_FOUND));
    }

    private Appointment createAppointmentEntity(AppointmentRequest request, User patient, DoctorSchedule availableSlot) {
        Appointment appointment = new Appointment();
        appointment.setProbableStartTime(request.getStartTime());
        appointment.setActualEndTime(request.getEndTime());
        appointment.setAppointmentTakenDate(request.getAppointmentTakenDate());
        appointment.setAppointmentBookedDate(LocalDateTime.now());
        appointment.setBookingType(request.getBookingType());
        appointment.setUser(patient);
        appointment.setAppointmentStatus(AppointmentStatusEnum.CONFIRMED);
        appointment.setDoctorSchedule(availableSlot);

        setAppointmentDetails(appointment, request, patient);

        return appointment;
    }

    private void setAppointmentDetails(Appointment appointment, AppointmentRequest request, User patient) {
        if (appointment.getBookingType() == BookingTypeEnum.OTHER_BOOKING) {
            appointment.setUsername(request.getPatientName());
            appointment.setPhone(request.getPatientPhone());
            appointment.setAddress(request.getPatientAddress());
            appointment.setDateOfBirth(request.getPatientDateOfBirth());
            appointment.setGender(request.getPatientGender());
        } else {
            appointment.setUsername(patient.getUsername());
            appointment.setPhone(patient.getPhone());
            appointment.setAddress(patient.getAddress());
            appointment.setDateOfBirth(patient.getDateOfBirth());
            appointment.setGender(patient.getGender());
        }
    }

    private void updatePatientInfo(User patient, AppointmentRequest request) {
        if (patient.getAddress() == null || patient.getDateOfBirth() == null || patient.getGender() == null) {
            patient.setAddress(request.getPatientAddress());
            patient.setDateOfBirth(request.getPatientDateOfBirth());
            patient.setGender(request.getPatientGender());
            userRepository.save(patient);
        }
    }

    private void updateDoctorSchedule(DoctorSchedule availableSlot, Appointment appointment) {
        availableSlot.getAppointments().add(appointment);
        availableSlot.setCurrentAppointment(availableSlot.getCurrentAppointment() + 1);

        doctorScheduleRepository.save(availableSlot);
    }

    private AppointmentResponse buildAppointmentResponse(Appointment appointment, User patient, User doctor, String message) {
        return AppointmentResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message(message)
                .probableStartTime(appointment.getProbableStartTime())
                .actualEndTime(appointment.getActualEndTime())
                .appointmentTakenDate(appointment.getAppointmentTakenDate())
                .bookingType(appointment.getBookingType())
                .username(appointment.getUsername())
                .phone(appointment.getPhone())
                .address(appointment.getAddress())
                .dateOfBirth(appointment.getDateOfBirth())
                .gender(appointment.getGender())
                .email(patient.getEmail())
                .doctorName(doctor.getUsername())
                .status(appointment.getAppointmentStatus())
                .build();
    }

    public boolean isUpcoming(Appointment appointment) {
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getAppointmentTakenDate(), appointment.getProbableStartTime());
        return appointmentDateTime.isAfter(LocalDateTime.now());
    }

    private void validateAppointment(Appointment appointment) {
        if (appointment.getProbableStartTime().isAfter(appointment.getActualEndTime())) {
            throw new ApiException(ErrorCodeEnum.INVALID_APPOINTMENT_TIME);
        }
        if (appointment.getAppointmentBookedDate().isAfter(
                LocalDateTime.of(appointment.getAppointmentTakenDate(), appointment.getProbableStartTime()))) {
            throw new ApiException(ErrorCodeEnum.INVALID_BOOKING_DATE);
        }
    }
}