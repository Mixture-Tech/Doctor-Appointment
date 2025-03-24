package mixture.hutech.backend.repository;

import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.entity.Appointment;
import mixture.hutech.backend.entity.DoctorSchedule;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import mixture.hutech.backend.enums.BookingTypeEnum;
import mixture.hutech.backend.enums.ClinicStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private User user;
    private DoctorSchedule doctorSchedule;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        // Tạo User
        user = User.builder()
                .email("john.doe@example.com")
                .username("johndoe")
                .build();
        entityManager.persist(user);

        // Tạo DoctorSchedule
        doctorSchedule = DoctorSchedule.builder()
                .user(User.builder().username("dr.smith").build())
                .build();
        entityManager.persist(doctorSchedule.getUser());
        entityManager.persist(doctorSchedule);

        // Tạo Appointment
        appointment = Appointment.builder()
                .username("John Doe")
                .phone("0123456789")
                .probableStartTime(LocalTime.of(10, 0))
                .appointmentTakenDate(LocalDate.now())
                .appointmentStatus(AppointmentStatusEnum.CONFIRMED)
                .bookingType(BookingTypeEnum.SELF_BOOKING)
                .clinicStatus(ClinicStatusEnum.PENDING)
                .user(user)
                .doctorSchedule(doctorSchedule)
                .appointmentCode("APT001")
                .build();
        entityManager.persist(appointment);
        entityManager.flush();
    }

    @Test
    void findById_shouldReturnAppointment_whenExists() {
        Optional<Appointment> result = appointmentRepository.findById(appointment.getId());

        assertTrue(result.isPresent());
        assertEquals(appointment.getId(), result.get().getId());
        assertEquals("John Doe", result.get().getUsername());
    }

    @Test
    void findByUserId_shouldReturnAppointments() {
        List<Appointment> result = appointmentRepository.findByUserId(user.getId());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(appointment.getId(), result.get(0).getId());
    }

    @Test
    void findUpcomingAppointments_shouldReturnAppointments() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(12, 0);

        List<Appointment> result = appointmentRepository.findUpcomingAppointments(start, end, AppointmentStatusEnum.CONFIRMED);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(appointment.getId(), result.get(0).getId());
    }

    @Test
    void listAppointmentByUserId_shouldReturnAppointmentResponses() {
        List<AppointmentResponse> result = appointmentRepository.listAppointmentByUserId(user.getEmail());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(appointment.getId(), result.get(0).getId());
        assertEquals("dr.smith", result.get(0).getDoctorName());
    }

    @Test
    void findByAppointmentTakenDateAndAppointmentStatus_shouldReturnAppointments() {
        List<Appointment> result = appointmentRepository.findByAppointmentTakenDateAndAppointmentStatus(
                LocalDate.now(), AppointmentStatusEnum.CONFIRMED);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(appointment.getId(), result.get(0).getId());
    }

    @Test
    void findAllAppointmentsPaginated_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AppointmentResponse> result = appointmentRepository.findAllAppointmentsPaginated(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(appointment.getId(), result.getContent().get(0).getId());
    }

    @Test
    void searchAppointments_withCodeAndUsername_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AppointmentResponse> result = appointmentRepository.searchAppointments("APT001", "John", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(appointment.getId(), result.getContent().get(0).getId());
    }

    @Test
    void searchAppointments_withNullParams_shouldReturnAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AppointmentResponse> result = appointmentRepository.searchAppointments(null, null, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(appointment.getId(), result.getContent().get(0).getId());
    }
}