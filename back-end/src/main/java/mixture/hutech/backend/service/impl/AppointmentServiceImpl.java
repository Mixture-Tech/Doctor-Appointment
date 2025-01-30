package mixture.hutech.backend.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.request.ClinicRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.entity.Appointment;
import mixture.hutech.backend.entity.DoctorSchedule;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import mixture.hutech.backend.enums.BookingTypeEnum;
import mixture.hutech.backend.enums.ClinicStatusEnum;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.AppointmentRepository;
import mixture.hutech.backend.repository.DoctorRepository;
import mixture.hutech.backend.repository.DoctorScheduleRepository;
import mixture.hutech.backend.repository.UserRepository;
import mixture.hutech.backend.service.AppointmentService;
import mixture.hutech.backend.service.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private static final int MAX_APPOINTMENTS_PER_SLOT = 3;
    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Create an appointment.
     */
    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request, String userEmail) {
        User patient = getUserByEmail(userEmail);
        User doctor = getDoctorById(request.getDoctor());

        // Thêm log để debug
        System.out.println("Doctor ID: " + doctor.getId());
        System.out.println("Day of week: " + request.getAppointmentTakenDate().getDayOfWeek().toString());
        System.out.println("Working date: " + request.getAppointmentTakenDate());
        System.out.println("Start time: " + request.getStartTime());
        System.out.println("End time: " + request.getEndTime());


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

        // Tính thời gian bắt đầu của lịch hẹn
        LocalDateTime appointmentStartDateTime = LocalDateTime.of(
                appointment.getAppointmentTakenDate(),
                appointment.getProbableStartTime()
        );

        // Kiểm tra thời hạn hủy trước 24 giờ
        if (appointmentStartDateTime.isBefore(LocalDateTime.now().plusHours(24))) {
            throw new ApiException(ErrorCodeEnum.CANNOT_CANCEL_WITHIN_24_HOURS);
        }

        appointment.setReminderSent(false);
        appointment.setAppointmentStatus(AppointmentStatusEnum.CANCELLED);
        appointmentRepository.save(appointment);

        DoctorSchedule doctorSchedule = appointment.getDoctorSchedule();
        doctorSchedule.setCurrentAppointment(doctorSchedule.getCurrentAppointment() - 1);
        doctorScheduleRepository.save(doctorSchedule);

        return buildAppointmentResponse(appointment, patient, doctorSchedule.getUser(), "Appointment has been cancelled");
    }

    @Override
    @Transactional
    public AppointmentResponse updateAppointment(String appointmentId, AppointmentRequest request, String userEmail) {
        User patient = getUserByEmail(userEmail);
        Appointment existingAppointment = getAppointmentById(appointmentId);

        if (!existingAppointment.getUser().getId().equals(patient.getId())) {
            throw new ApiException(ErrorCodeEnum.AUTHENTICATION_FAILED);
        }

//        if (!isUpcoming(existingAppointment)) {
//            throw new ApiException(ErrorCodeEnum.CANNOT_MODIFY_PAST_APPOINTMENT);
//        }

        User doctor = existingAppointment.getDoctorSchedule().getUser();
        DoctorSchedule newAvailableSlot = getAvailableSlot(doctor.getId(), request);

        if (!existingAppointment.getDoctorSchedule().getId().equals(newAvailableSlot.getId())) {
            DoctorSchedule oldSlot = existingAppointment.getDoctorSchedule();
            oldSlot.setCurrentAppointment(oldSlot.getCurrentAppointment() - 1);
            doctorScheduleRepository.save(oldSlot);

            existingAppointment.setDoctorSchedule(newAvailableSlot);
        }

        existingAppointment.setProbableStartTime(request.getStartTime());
        existingAppointment.setActualEndTime(request.getEndTime());
        existingAppointment.setAppointmentTakenDate(request.getAppointmentTakenDate());

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        updateDoctorSchedule(newAvailableSlot, updatedAppointment);

        try {
            sendUpdateConfirmationEmail(patient, doctor, updatedAppointment);
        } catch (MessagingException e) {
            System.out.println("Mail error: " + e.getMessage());
        }

        return buildAppointmentResponse(updatedAppointment, patient, doctor, "Appointment updated successfully");
    }

    // Add a new method to send update confirmation email
    private void sendUpdateConfirmationEmail(User patient, User doctor, Appointment appointment) throws MessagingException {
        emailService.sendMailAppointmentUpdate(
                patient.getEmail(),
                patient.getUsername(),
                doctor.getUsername(),
                appointment.getAppointmentTakenDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                appointment.getProbableStartTime().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - " + appointment.getActualEndTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
        );
    }

    //    @Override
    public List<AppointmentResponse> getAppointmentsByUserId(String userId) {
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);

        return appointments.stream()
                .map(appointment -> buildAppointmentResponse(
                        appointment,
                        appointment.getUser(),
                        appointment.getDoctorSchedule().getUser(),
                        "Appointment fetched successfully"
                ))
                .collect(Collectors.toList());
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
        return doctorRepository.findDoctorById(doctorId)
                .orElseThrow(() -> new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND));
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
//        appointment.setUser(request);
        appointment.setUsername(request.getPatientName());
        appointment.setAddress(request.getPatientAddress());
        appointment.setDateOfBirth(request.getPatientDateOfBirth());
        appointment.setGender(request.getPatientGender());
//        appointment.setPhone(patient.getPhone());
        appointment.setUser(patient);
        appointment.setAppointmentStatus(AppointmentStatusEnum.CONFIRMED);
        appointment.setClinicStatus(ClinicStatusEnum.PENDING);

        String appointmentCode = "MTAP" + String.format("%06d", Math.abs(secureRandom.nextInt(1000000)));
        appointment.setAppointmentCode(appointmentCode);
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
            appointment.setAddress(Optional.ofNullable(patient.getAddress()).orElse(request.getPatientAddress()));
            appointment.setDateOfBirth(Optional.ofNullable(patient.getDateOfBirth()).orElse(request.getPatientDateOfBirth()));
            appointment.setGender(Optional.ofNullable(patient.getGender()).orElse(request.getPatientGender()));
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
//                .errorCode(ErrorCodeEnum.OK)
//                .message(message)
                .id(appointment.getId())
                .probableStartTime(appointment.getProbableStartTime())
                .actualEndTime(appointment.getActualEndTime())
                .appointmentTakenDate(appointment.getAppointmentTakenDate())
                .bookingType(appointment.getBookingType())
                .username(appointment.getUser().getUsername())
                .phone(appointment.getUser().getPhone())
                .address(appointment.getUser().getAddress())
                .dateOfBirth(appointment.getUser().getDateOfBirth())
                .gender(appointment.getUser().getGender())
                .email(patient.getEmail())
                .doctorName(doctor.getUsername())
                .status(appointment.getAppointmentStatus())
                .appointmentCode(appointment.getAppointmentCode())
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


    @Override
    public List<AppointmentResponse> listAppointmentByUser(String userEmail) {
        List<AppointmentResponse> appointments = appointmentRepository.listAppointmentByUserId(userEmail);
        if(appointments.isEmpty()){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FOUND);
        }
        return appointments;
    }

    /**
     * Management Clinic Status.
     */

    @Override
    public Page<AppointmentResponse> listAppointmentsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AppointmentResponse> appointments = appointmentRepository.findAllAppointmentsPaginated(pageable);

        if(appointments.isEmpty()){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FOUND);
        }

        return appointments;
    }

    @Override
    @Transactional
    public AppointmentResponse updateClinicStatus(String appointmentId, ClinicStatusEnum clinicStatusEnum) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FOUND)
        );

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if(clinicStatusEnum != ClinicStatusEnum.COMPLETED && clinicStatusEnum != ClinicStatusEnum.NO_SHOW){
            throw new ApiException(ErrorCodeEnum.INVALID_CLINIC_STATUS);
        }

        if(appointment.getClinicStatus() == ClinicStatusEnum.NO_SHOW){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_ALREADY_NO_SHOW);
        }

        if(appointment.getClinicStatus() == ClinicStatusEnum.COMPLETED){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_ALREADY_COMPLETED);
        }

        if(appointment.getAppointmentTakenDate().isAfter(currentDate)){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_HAPPENED);
        }

        if (appointment.getAppointmentTakenDate().isEqual(currentDate)) {
            LocalTime appointmentStart = appointment.getProbableStartTime();
            LocalTime appointmentEnd = appointment.getActualEndTime();

            if (clinicStatusEnum == ClinicStatusEnum.COMPLETED) {
                if (currentTime.isBefore(appointmentEnd)) {
                    throw new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FINISHED);
                }
            }
        }

        if(appointment.getAppointmentStatus() == AppointmentStatusEnum.CANCELLED){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_CANCELLED);
        }

        appointment.setClinicStatus(clinicStatusEnum);
        appointment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        appointmentRepository.save(appointment);

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .probableStartTime(appointment.getProbableStartTime())
                .actualEndTime(appointment.getActualEndTime())
                .appointmentTakenDate(appointment.getAppointmentTakenDate())
                .bookingType(appointment.getBookingType())
                .username(appointment.getUser().getUsername())
                .phone(appointment.getUser().getPhone())
                .address(appointment.getUser().getAddress())
                .dateOfBirth(appointment.getUser().getDateOfBirth())
                .gender(appointment.getUser().getGender())
                .email(appointment.getUser().getEmail())
                .doctorName(appointment.getDoctorSchedule().getUser().getUsername())
                .status(appointment.getAppointmentStatus())
                .clinicStatus(appointment.getClinicStatus())
                .appointmentCode(appointment.getAppointmentCode())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    @Override
    public Page<AppointmentResponse> searchAppointments(String appointmentCode, String username, int page, int size) {
        if(appointmentCode != null && appointmentCode.trim().isEmpty()){
            appointmentCode = null;
        }
        if(username != null && username.trim().isEmpty()){
            username = null;
        }

        if(appointmentCode == null && username == null){
            throw new ApiException(ErrorCodeEnum.SEARCH_CRITERIA_REQUIRED);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AppointmentResponse> responses = appointmentRepository.searchAppointments(appointmentCode, username, pageable);

        if(responses.isEmpty()){
            throw new ApiException(ErrorCodeEnum.APPOINTMENT_NOT_FOUND);
        }

        return responses;
    }


}