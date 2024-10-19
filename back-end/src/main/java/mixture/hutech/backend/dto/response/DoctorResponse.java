package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    @JsonProperty("doctor_id")
    private String doctorId;
    @JsonProperty("doctor_name")
    private String doctorName;
    @JsonProperty("doctor_description")
    private String doctorDescription;
    @JsonProperty("doctor_image")
    private String doctorImage;
}
