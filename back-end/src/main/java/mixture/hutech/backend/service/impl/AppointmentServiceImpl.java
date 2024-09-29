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


    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request, String userEmail) {
        User patient = getUserByEmail(userEmail);
        User doctor = getDoctorById(request.getDoctor());
        DoctorSchedule availableSlot = getAvailableSlot(doctor.getId(), request);

        Appointment appointment = createAppointmentEntity(request, patient, availableSlot);
        updatePatientInfo(patient, request);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        updateDoctorSchedule(availableSlot, appointment);

        try {
            emailService.sendAppointmentConfirmation(
                    patient.getEmail(),
                    patient.getUsername(),
                    doctor.getUsername(),
                    savedAppointment.getAppointmentTakenDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    savedAppointment.getProbableStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
            );
        }catch (MessagingException e){
            System.out.println("Mail error: " + e.getMessage());
        }

        return buildAppointmentResponse(appointment, patient, doctor);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCodeEnum.USER_NOT_FOUND));
    }

    private User getDoctorById(String doctorId) {
        return userRepository.findDoctorById(doctorId)
                .orElseThrow(() -> new ApiException(ErrorCodeEnum.DOCTOR_SCHEDULE_NOT_FOUND));
    }

    private DoctorSchedule getAvailableSlot(String doctorId, AppointmentRequest request) {
        return doctorScheduleRepository.findAvailableSlotByDoctorAndDayAndHours(
                doctorId,
                request.getAppointmentTakenDate().getDayOfWeek().toString(),
                request.getAppointmentTakenDate(),
                request.getStartTime(),
                request.getEndTime()
        ).orElseThrow(() -> new ApiException(ErrorCodeEnum.DOCTOR_SCHEDULE_NOT_FOUND));
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
        availableSlot.setAppointment(appointment);
        doctorScheduleRepository.save(availableSlot);
    }

    private AppointmentResponse buildAppointmentResponse(Appointment appointment, User patient, User doctor) {
        return AppointmentResponse.builder()
                .errorCode(ErrorCodeEnum.OK)
                .message("Appointment created successfully")
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

    // validate appointment

//    private Appointment getAppointmentById(String id){
//        return appointmentRepository.findById(id).orElseThrow();
//    }
//
//    /**
//     * Checks if the appointment is upcoming.
//     */
//    public boolean isUpcoming(Appointment appointment) {
//        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getAppointmentTakenDate(), appointment.getProbableStartTime());
//        return appointmentDateTime.isAfter(LocalDateTime.now());
//    }
//
//    /**
//     * Checks if the appointment is in the past.
//     */
//    public boolean isPast(Appointment appointment) {
//        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getAppointmentTakenDate(), appointment.getActualEndTime());
//        return appointmentDateTime.isBefore(LocalDateTime.now());
//    }
//
//    /**
//     * Cancels an upcoming appointment.
//     */
//    @Transactional
//    public void cancelAppointment(String appointmentId) {
//        Appointment appointment = getAppointmentById(appointmentId);
//        if (isUpcoming(appointment)) {
//            appointment.setAppointmentStatus(AppointmentStatusEnum.CANCELLED);
//            appointmentRepository.save(appointment);
//            // TODO: Notify the doctor and patient about the cancellation
//        } else {
//            throw new AppointmentException("Cannot cancel a past or ongoing appointment");
//        }
//    }
//
//    @Transactional
//    public void completeAppointment(String appointmentId) {
//        Appointment appointment = getAppointmentById(appointmentId);
//        if (isPast(appointment)) {
//            appointment.setAppointmentStatus(AppointmentStatusEnum.COMPLETED);
//            appointmentRepository.save(appointment);
//            // TODO: Update any post-appointment processes (e.g., billing, follow-up scheduling)
//        } else {
//            throw new AppointmentException("Cannot mark an upcoming or ongoing appointment as completed");
//        }
//    }
//
//    @Transactional
//    public void rescheduleAppointment(String appointmentId, LocalDate newDate, LocalTime newStartTime, LocalTime newEndTime) {
//        Appointment appointment = getAppointmentById(appointmentId);
//        if (isUpcoming(appointment)) {
//            appointment.setAppointmentTakenDate(newDate);
//            appointment.setProbableStartTime(newStartTime);
//            appointment.setActualEndTime(newEndTime);
//            appointment.setAppointmentStatus(AppointmentStatusEnum.RESCHEDULED);
//            validateAppointment(appointment);
//            if (hasConflictingAppointments(appointment)) {
//                throw new ApiException(ErrorCodeEnum.APPOINTMENT_CONFLICT);
//            }
//            appointmentRepository.save(appointment);
//            // TODO: Notify the doctor and patient about the rescheduling
//        } else {
//            throw new AppointmentException("Cannot reschedule a past or ongoing appointment");
//        }
//    }
//
//    /**
//     * Validates the appointment details.
//     */
//    private void validateAppointment(Appointment appointment) {
//        if (appointment.getProbableStartTime().isAfter(appointment.getActualEndTime())) {
//            throw new ApiException(ErrorCodeEnum.INVALID_APPOINTMENT_TIME);
//        }
//        if (appointment.getAppointmentBookedDate().isAfter(
//                LocalDateTime.of(appointment.getAppointmentTakenDate(), appointment.getProbableStartTime()))) {
//            throw new ApiException(ErrorCodeEnum.INVALID_BOOKING_DATE);
//        }
//    }
}