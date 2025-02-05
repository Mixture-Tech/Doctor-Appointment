package mixture.hutech.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mixture.hutech.backend.enums.ClinicStatusEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicRequest {
    @JsonProperty("clinic_status")
    private ClinicStatusEnum clinicStatusEnum;
}
