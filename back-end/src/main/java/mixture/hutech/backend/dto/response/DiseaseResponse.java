package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mixture.hutech.backend.entity.Disease;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.DiseaseRepository;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseResponse {
    @JsonProperty("disease_id")
    private int diseaseId;
    @JsonProperty("disease_eng_name")
    private String englishDisease;
    @JsonProperty("disease_vie_name")
    private String vietnameseDisease;
    @JsonProperty("extractedSymptoms")
    private List<String> extractedSymptoms;
    @JsonProperty("specialization")
    private SpecializationResponse specialization;

    public DiseaseResponse(int diseaseId, String vietnameseDisease){
        this.diseaseId = diseaseId;
        this.vietnameseDisease = vietnameseDisease;
    }
}
