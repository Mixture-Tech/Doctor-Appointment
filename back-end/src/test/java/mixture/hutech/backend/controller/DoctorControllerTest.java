package mixture.hutech.backend.controller;

import mixture.hutech.backend.dto.response.DoctorResponse;
import mixture.hutech.backend.dto.response.DoctorScheduleResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.print.Doc;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllDoctor_ShouldReturnListOfDoctors_WhenDoctorsAreFound(){
        List<DoctorScheduleResponse> schedule1 = Arrays.asList(
                DoctorScheduleResponse.builder()
                        .id("1")
                        .dayOfWeek(String.valueOf(DayOfWeek.MONDAY))
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(11, 0))
                        .workingDate(LocalDate.now())
                        .currentAppointment(2)
                        .build()
        );

        List<DoctorScheduleResponse> schedule2 = Arrays.asList(
                DoctorScheduleResponse.builder()
                        .id("2")
                        .dayOfWeek(String.valueOf(DayOfWeek.THURSDAY))
                        .startTime(LocalTime.of(13, 0))
                        .endTime(LocalTime.of(15, 0))
                        .workingDate(LocalDate.now())
                        .currentAppointment(3)
                        .build()
        );

        List<DoctorResponse> doctors = Arrays.asList(
                DoctorResponse.builder()
                        .doctorId("DT1")
                        .doctorName("Doctor 1")
                        .doctorDescription("Doctor Description 1")
                        .doctorImage(null)
                        .specializationName("Specialization 1")
                        .schedules(schedule1)
                        .build(),
                DoctorResponse.builder()
                        .doctorId("DT2")
                        .doctorName("Doctor 2")
                        .doctorDescription("Doctor Description 2")
                        .doctorImage(null)
                        .specializationName("Specialization 2")
                        .schedules(schedule2)
                        .build()
        );

        when(doctorService.listDoctor()).thenReturn(doctors);

        ResponseEntity<MessageResponse> responseEntity = doctorController.findAllDoctor();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MessageResponse messageResponse = responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals(ErrorCodeEnum.OK, messageResponse.getErrorCode());
        assertEquals(ErrorCodeEnum.OK.getMessage(), messageResponse.getMessage());

        @SuppressWarnings("unchecked")
        List<DoctorResponse> resultList = (List<DoctorResponse>) messageResponse.getData();
        assertEquals(2, resultList.size());

        // Verify doctor 1
        assertEquals("DT1", resultList.get(0).getDoctorId());
        assertEquals("Doctor 1", resultList.get(0).getDoctorName());
        assertEquals("Specialization 1", resultList.get(0).getSpecializationName());
        assertEquals(1, resultList.get(0).getSchedules().size());

        // Verify doctor 2
        assertEquals("DT2", resultList.get(1).getDoctorId());
        assertEquals("Doctor 2", resultList.get(1).getDoctorName());
        assertEquals("Specialization 2", resultList.get(1).getSpecializationName());
        assertEquals(1, resultList.get(1).getSchedules().size());

        verify(doctorService, times(1)).listDoctor();

    }

    @Test
    @DisplayName("Should return error response when ApiException is thrown")
    void findAllDoctor_ShouldReturnErrorResponse_WhenApiExceptionIsThrown() {
        // Arrange
        when(doctorService.listDoctor()).thenThrow(new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND));

        // Act
        ResponseEntity<MessageResponse> responseEntity = doctorController.findAllDoctor();

        // Assert
        assertNotNull(responseEntity);
        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND.getHttpStatus(), responseEntity.getStatusCode());

        MessageResponse messageResponse = responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND, messageResponse.getErrorCode());
        assertEquals(ErrorCodeEnum.DOCTOR_NOT_FOUND.getMessage(), messageResponse.getMessage());
        assertNull(messageResponse.getData());

        verify(doctorService, times(1)).listDoctor();
    }
}
