package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleResponse {
    @JsonProperty("doctor_schedule_id")
    private String id;
    @JsonProperty("day_of_week")
    private String dayOfWeek;
    @JsonProperty("start_time")
    private LocalTime startTime;
    @JsonProperty("end_time")
    private LocalTime endTime;
    @JsonProperty("working_date")
    private LocalDate workingDate;
    @JsonProperty("doctor_name")
    private String doctorName;
}
