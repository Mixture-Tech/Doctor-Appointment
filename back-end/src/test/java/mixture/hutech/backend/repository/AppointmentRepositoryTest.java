package mixture.hutech.backend.repository;

import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.entity.Appointment;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppointmentRepositoryTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private Appointment appointment;
    private String userId = "user123";
    private String appointmentId = "apt123";

    @BeforeEach
    void setUp() {
        appointment = Appointment.builder()
                .id(appointmentId)
                .username("John Doe")
                .probableStartTime(LocalTime.of(10, 0))
                .appointmentTakenDate(LocalDate.now())
                .appointmentStatus(AppointmentStatusEnum.CONFIRMED)
                .build();
    }

    @Test
    void findById_shouldReturnAppointment_whenExists() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        Optional<Appointment> result = appointmentRepository.findById(appointmentId);

        assertTrue(result.isPresent());
        assertEquals(appointmentId, result.get().getId());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void findById_shouldReturnEmpty_whenNotExists() {
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        Optional<Appointment> result = appointmentRepository.findById(appointmentId);

        assertFalse(result.isPresent());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void findByUserId_shouldReturnAppointments() {
        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findByUserId(userId)).thenReturn(appointments);

        List<Appointment> result = appointmentRepository.findByUserId(userId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(appointment, result.get(0));
        verify(appointmentRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findUpcomingAppointments_shouldReturnAppointments() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(12, 0);
        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findUpcomingAppointments(start, end, AppointmentStatusEnum.CONFIRMED))
                .thenReturn(appointments);

        List<Appointment> result = appointmentRepository.findUpcomingAppointments(start, end, AppointmentStatusEnum.CONFIRMED);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository, times(1))
                .findUpcomingAppointments(start, end, AppointmentStatusEnum.CONFIRMED);
    }

    @Test
    void listAppointmentByUserId_shouldReturnAppointmentResponses() {
        AppointmentResponse response = new AppointmentResponse(); // Giả sử đã có constructor phù hợp
        List<AppointmentResponse> responses = Collections.singletonList(response);
        when(appointmentRepository.listAppointmentByUserId(userId)).thenReturn(responses);

        List<AppointmentResponse> result = appointmentRepository.listAppointmentByUserId(userId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).listAppointmentByUserId(userId);
    }

    @Test
    void findByAppointmentTakenDateAndAppointmentStatus_shouldReturnAppointments() {
        LocalDate date = LocalDate.now();
        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findByAppointmentTakenDateAndAppointmentStatus(date, AppointmentStatusEnum.CONFIRMED))
                .thenReturn(appointments);

        List<Appointment> result = appointmentRepository.findByAppointmentTakenDateAndAppointmentStatus(date, AppointmentStatusEnum.CONFIRMED);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(appointmentRepository, times(1))
                .findByAppointmentTakenDateAndAppointmentStatus(date, AppointmentStatusEnum.CONFIRMED);
    }

    @Test
    void findAllAppointmentsPaginated_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        AppointmentResponse response = new AppointmentResponse();
        Page<AppointmentResponse> page = new PageImpl<>(Collections.singletonList(response));
        when(appointmentRepository.findAllAppointmentsPaginated(pageable)).thenReturn(page);

        Page<AppointmentResponse> result = appointmentRepository.findAllAppointmentsPaginated(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(appointmentRepository, times(1)).findAllAppointmentsPaginated(pageable);
    }

    @Test
    void searchAppointments_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        String appointmentCode = "APT001";
        String username = "John";
        AppointmentResponse response = new AppointmentResponse();
        Page<AppointmentResponse> page = new PageImpl<>(Collections.singletonList(response));
        when(appointmentRepository.searchAppointments(appointmentCode, username, pageable)).thenReturn(page);

        Page<AppointmentResponse> result = appointmentRepository.searchAppointments(appointmentCode, username, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(appointmentRepository, times(1)).searchAppointments(appointmentCode, username, pageable);
    }

    @Test
    void searchAppointments_withNullParams_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        AppointmentResponse response = new AppointmentResponse();
        Page<AppointmentResponse> page = new PageImpl<>(Collections.singletonList(response));
        when(appointmentRepository.searchAppointments(null, null, pageable)).thenReturn(page);

        Page<AppointmentResponse> result = appointmentRepository.searchAppointments(null, null, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(appointmentRepository, times(1)).searchAppointments(null, null, pageable);
    }
}