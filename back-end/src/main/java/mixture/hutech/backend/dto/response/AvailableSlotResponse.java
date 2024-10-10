package mixture.hutech.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableSlotResponse {
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
}