package mixture.hutech.backend.controller;

import mixture.hutech.backend.dto.request.AppointmentRequest;
import mixture.hutech.backend.dto.response.AppointmentResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.entity.CustomUserDetail;
import mixture.hutech.backend.entity.User;
import mixture.hutech.backend.enums.AppointmentStatusEnum;
import mixture.hutech.backend.enums.BookingTypeEnum;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    private CustomUserDetail userDetail;
    private AppointmentRequest appointmentRequest;
    private AppointmentResponse appointmentResponse;

    @BeforeEach
    void setUp() {
        // Khởi tạo User
        User user = new User();
        user.setId("user123");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setActive(true);
        // Không cần set Role vì chúng ta không test authority trong trường hợp này

        // Khởi tạo CustomUserDetail
        userDetail = new CustomUserDetail(user);

        // Khởi tạo AppointmentRequest
        appointmentRequest = AppointmentRequest.builder()
                .doctor("doctor123")
                .appointmentTakenDate(LocalDate.of(2025, 3, 20))
                .appointmentBookedDate(LocalDate.of(2025, 3, 18))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(9, 30))
                .bookingType(BookingTypeEnum.SELF_BOOKING)
                .patientName("Test Patient")
                .patientPhone("0123456789")
                .patientAddress("123 Test Street")
                .patientDateOfBirth(LocalDate.of(1990, 1, 1))
                .patientGender("Male")
                .status(AppointmentStatusEnum.CONFIRMED)
                .build();

        // Khởi tạo AppointmentResponse
        appointmentResponse = AppointmentResponse.builder()
                .id("appointment123")
                .probableStartTime(LocalTime.of(9, 0))
                .actualEndTime(LocalTime.of(9, 30))
                .appointmentTakenDate(LocalDate.of(2025, 3, 20))
                .bookingType(BookingTypeEnum.SELF_BOOKING)
                .username("Test Patient")
                .phone("0123456789")
                .address("123 Test Street")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender("Male")
                .email("test@example.com")
                .doctorName("Dr. Test")
                .status(AppointmentStatusEnum.CONFIRMED)
                .appointmentCode("MTAP123456")
                .build();
    }

    @Test
    void createAppointment_Success() {
        // Arrange
        when(appointmentService.createAppointment(any(AppointmentRequest.class), eq("test@example.com")))
                .thenReturn(appointmentResponse);

        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "doctor123",
                appointmentRequest,
                userDetail
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.OK, response.getBody().getErrorCode());
        assertEquals(ErrorCodeEnum.OK.getMessage(), response.getBody().getMessage());
        assertEquals(appointmentResponse, response.getBody().getData());

        verify(appointmentService, times(1))
                .createAppointment(any(AppointmentRequest.class), eq("test@example.com"));
    }

    @Test
    void createAppointment_ApiException() {
        // Arrange
        ApiException apiException = new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND);
        when(appointmentService.createAppointment(any(AppointmentRequest.class), eq("test@example.com")))
                .thenThrow(apiException);

        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "doctor123",
                appointmentRequest,
                userDetail
        );

        // Assert
        assertEquals(apiException.getErrorCodeEnum().getHttpStatus(), response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND, response.getBody().getErrorCode());
        assertEquals(apiException.getMessage(), response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(appointmentService, times(1))
                .createAppointment(any(AppointmentRequest.class), eq("test@example.com"));
    }

    @Test
    void createAppointment_InvalidInput_NullRequest() {
        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "doctor123",
                null,
                userDetail
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.EXTERNAL_SERVICE_ERROR, response.getBody().getErrorCode());
        assertTrue(response.getBody().getMessage().contains("An unexpected error occurred"));
        assertNull(response.getBody().getData());

        verify(appointmentService, never())
                .createAppointment(any(AppointmentRequest.class), anyString());
    }

    @Test
    void createAppointment_InvalidDoctorId() {
        // Arrange
        ApiException apiException = new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND);
        when(appointmentService.createAppointment(any(AppointmentRequest.class), eq("test@example.com")))
                .thenThrow(apiException);

        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "invalid-doctor-id",
                appointmentRequest,
                userDetail
        );

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND, response.getBody().getErrorCode());
        assertNull(response.getBody().getData());

        verify(appointmentService, times(1))
                .createAppointment(any(AppointmentRequest.class), eq("test@example.com"));
    }

    @Test
    void createAppointment_UnauthenticatedUser() {
        // Arrange
        CustomUserDetail nullUserDetail = null;

        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "doctor123",
                appointmentRequest,
                nullUserDetail
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.EXTERNAL_SERVICE_ERROR, response.getBody().getErrorCode());
        assertTrue(response.getBody().getMessage().contains("An unexpected error occurred"));
        assertNull(response.getBody().getData());

        verify(appointmentService, never())
                .createAppointment(any(AppointmentRequest.class), anyString());
    }

    @Test
    void createAppointment_FullSlot() {
        // Arrange
        ApiException apiException = new ApiException(ErrorCodeEnum.FULL_SLOT);
        when(appointmentService.createAppointment(any(AppointmentRequest.class), eq("test@example.com")))
                .thenThrow(apiException);

        // Act
        ResponseEntity<MessageResponse> response = appointmentController.createAppointment(
                "doctor123",
                appointmentRequest,
                userDetail
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCodeEnum.FULL_SLOT, response.getBody().getErrorCode());
        assertNull(response.getBody().getData());

        verify(appointmentService, times(1))
                .createAppointment(any(AppointmentRequest.class), eq("test@example.com"));
    }

    @Test
    void createAppointment_DatabaseError() {
        // Arrange
        RuntimeException databaseException = new RuntimeException("Database connection failed");
        when(appointmentService.createAppointment(any(AppointmentRequest.class), eq("test@example.com")))
                .thenThrow(databaseException);

        // Act
        ResponseEntity<MessageResponse> response;
        try {
            response = appointmentController.createAppointment(
                    "doctor123",
                    appointmentRequest,
                    userDetail
            );
        } catch (RuntimeException e) {
            response = null; // Để mô phỏng việc controller không xử lý được lỗi
        }

        // Assert
        assertNotNull(response, "Expected a proper ResponseEntity, but controller threw an unhandled exception");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(),
                "Expected controller to return 500 Internal Server Error for unexpected exceptions");
        assertEquals(ErrorCodeEnum.EXTERNAL_SERVICE_ERROR, response.getBody().getErrorCode());
        assertTrue(response.getBody().getMessage().contains("Database connection failed"));

        verify(appointmentService, times(1))
                .createAppointment(any(AppointmentRequest.class), eq("test@example.com"));
    }
}