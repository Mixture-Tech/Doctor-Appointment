package mixture.hutech.backend.service.impl;

import mixture.hutech.backend.dto.request.AppointmentRequest;
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
import mixture.hutech.backend.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private EmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorScheduleRepository doctorScheduleRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private User patient;
    private User doctor;
    private AppointmentRequest appointmentRequest;
    private DoctorSchedule doctorSchedule;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        // Khởi tạo Patient
        patient = new User();
        patient.setId("patient123");
        patient.setEmail("patient@example.com");
        patient.setUsername("Patient Name");

        // Khởi tạo Doctor
        doctor = new User();
        doctor.setId("doctor123");
        doctor.setUsername("Dr. Test");

        // Khởi tạo AppointmentRequest
        appointmentRequest = AppointmentRequest.builder()
                .doctor("doctor123")
                .appointmentTakenDate(LocalDate.of(2025, 3, 20))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(9, 30))
                .bookingType(BookingTypeEnum.SELF_BOOKING)
                .patientName("Patient Name")
                .patientPhone("0123456789")
                .patientAddress("123 Test Street")
                .patientDateOfBirth(LocalDate.of(1990, 1, 1))
                .patientGender("Male")
                .build();

        // Khởi tạo DoctorSchedule với builder pattern
        doctorSchedule = DoctorSchedule.builder()
                .id("schedule123")
                .user(doctor)
                .dayOfWeek("TUESDAY")
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .isAvailable(true)
                .workingDate(LocalDate.of(2025, 3, 20))
                .currentAppointment(0)
                .appointments(new ArrayList<>()) // Khởi tạo appointments
                .build();

        // Khởi tạo Appointment
        appointment = new Appointment();
        appointment.setId("appointment123");
        appointment.setUser(patient);
        appointment.setDoctorSchedule(doctorSchedule);
        appointment.setProbableStartTime(LocalTime.of(9, 0));
        appointment.setActualEndTime(LocalTime.of(9, 30));
        appointment.setAppointmentTakenDate(LocalDate.of(2025, 3, 20));
        appointment.setAppointmentStatus(AppointmentStatusEnum.CONFIRMED);
        appointment.setClinicStatus(ClinicStatusEnum.PENDING);
    }

    @Test
    void createAppointment_Success() throws Exception {
        // Arrange
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(doctorRepository.findDoctorById("doctor123")).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.findAvailableSlotByDoctorAndDayAndHours(
                eq("doctor123"), anyString(), any(), any(), any()))
                .thenReturn(Optional.of(doctorSchedule));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        AppointmentResponse response = appointmentService.createAppointment(appointmentRequest, "patient@example.com");

        // Assert
        assertNotNull(response);
        assertEquals("appointment123", response.getId());
        assertEquals(AppointmentStatusEnum.CONFIRMED, response.getStatus());
        assertEquals("Dr. Test", response.getDoctorName());

        verify(userRepository).findByEmail("patient@example.com");
        verify(doctorRepository).findDoctorById("doctor123");
        verify(doctorScheduleRepository).findAvailableSlotByDoctorAndDayAndHours(
                eq("doctor123"), anyString(), any(), any(), any());
        verify(appointmentRepository).save(any(Appointment.class));
        verify(doctorScheduleRepository).save(doctorSchedule);
        verify(emailService).sendMailAppointmentConfirmation(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void createAppointment_UserNotFound() {
        // Arrange
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            appointmentService.createAppointment(appointmentRequest, "patient@example.com");
        });

        assertEquals(ErrorCodeEnum.USER_NOT_FOUND, exception.getErrorCodeEnum());
        verify(userRepository).findByEmail("patient@example.com");
        verifyNoInteractions(doctorRepository, doctorScheduleRepository, appointmentRepository);
    }

    @Test
    void createAppointment_DoctorNotFound() {
        // Arrange
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(doctorRepository.findDoctorById("doctor123")).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            appointmentService.createAppointment(appointmentRequest, "patient@example.com");
        });

        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND, exception.getErrorCodeEnum(), "Test case should be Doctor Not Found");
        verify(userRepository).findByEmail("patient@example.com");
        verify(doctorRepository).findDoctorById("doctor123");
        verifyNoInteractions(doctorScheduleRepository, appointmentRepository);
    }

    @Test
    void createAppointment_FullSlot() {
        // Arrange
        doctorSchedule.setCurrentAppointment(3); // MAX_APPOINTMENTS_PER_SLOT = 3
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(doctorRepository.findDoctorById("doctor123")).thenReturn(Optional.of(doctor));
        when(doctorScheduleRepository.findAvailableSlotByDoctorAndDayAndHours(
                eq("doctor123"), anyString(), any(), any(), any()))
                .thenReturn(Optional.of(doctorSchedule));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            appointmentService.createAppointment(appointmentRequest, "patient@example.com");
        });

        assertEquals(ErrorCodeEnum.FULL_SLOT, exception.getErrorCodeEnum());
        verifyNoInteractions(appointmentRepository);
    }

    @Test
    void cancelAppointment_Success() {
        // Arrange
        appointment.setAppointmentTakenDate(LocalDate.now().plusDays(2)); // Future date
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById("appointment123")).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        AppointmentResponse response = appointmentService.cancelAppointment("appointment123", "patient@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(AppointmentStatusEnum.CANCELLED, response.getStatus());
        verify(appointmentRepository).save(any(Appointment.class));
        verify(doctorScheduleRepository).save(doctorSchedule);
    }

    @Test
    void cancelAppointment_PastAppointment() {
        // Arrange
        appointment.setAppointmentTakenDate(LocalDate.now().minusDays(1)); // Past date
        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById("appointment123")).thenReturn(Optional.of(appointment));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            appointmentService.cancelAppointment("appointment123", "patient@example.com");
        });

        assertEquals(ErrorCodeEnum.CANNOT_CANCEL_PAST_APPOINTMENT, exception.getErrorCodeEnum());
        verifyNoMoreInteractions(appointmentRepository);
    }

    @Test
    void updateAppointment_Success() throws Exception {
        // Arrange
        AppointmentRequest updateRequest = AppointmentRequest.builder()
                .appointmentTakenDate(LocalDate.of(2025, 3, 21))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .build();

        DoctorSchedule newSchedule = DoctorSchedule.builder()
                .id("schedule456")
                .user(doctor)
                .dayOfWeek("WEDNESDAY")
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(17, 0))
                .isAvailable(true)
                .workingDate(LocalDate.of(2025, 3, 21))
                .currentAppointment(0)
                .appointments(new ArrayList<>())
                .build();

        when(userRepository.findByEmail("patient@example.com")).thenReturn(Optional.of(patient));
        when(appointmentRepository.findById("appointment123")).thenReturn(Optional.of(appointment));
        when(doctorScheduleRepository.findAvailableSlotByDoctorAndDayAndHours(
                eq("doctor123"), anyString(), any(), any(), any()))
                .thenReturn(Optional.of(newSchedule));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(doctorScheduleRepository.save(any(DoctorSchedule.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save trả về chính object

        // Act
        AppointmentResponse response = appointmentService.updateAppointment("appointment123", updateRequest, "patient@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(LocalTime.of(10, 0), response.getProbableStartTime());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(doctorScheduleRepository, times(2)).save(any(DoctorSchedule.class)); // Mong đợi 2 lần save
        verify(emailService, times(1)).sendMailAppointmentUpdate(anyString(), anyString(), anyString(), anyString(), anyString());
    }
}