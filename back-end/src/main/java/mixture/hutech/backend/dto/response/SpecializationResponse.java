package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mixture.hutech.backend.enums.ErrorCodeEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecializationResponse {
    @JsonProperty("specialization_id")
    private int specializationId;
    @JsonProperty("specialization_name")
    private String specializationName;
}
