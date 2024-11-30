package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mixture.hutech.backend.service.CustomTranslateService;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseResponse {
    @JsonProperty("disease_id")
    private String diseaseId;
    @JsonProperty("disease_name")
    private String diseaseName;

    private int disease_Id;
    private List<String> extractedSymptoms;
    private String englishDisease;
    private String vietnameseDisease;

    public DiseaseResponse(String diseaseId, String diseaseName){
        this.diseaseId = diseaseId;
        this.diseaseName = diseaseName;
    }

    public DiseaseResponse(int disease_Id, List<String> extractedSymptoms, CustomTranslateService translateService) {
        this.disease_Id = disease_Id;
        this.extractedSymptoms = extractedSymptoms;
        this.englishDisease = translateService.getDiseaseNameById(disease_Id);
        this.vietnameseDisease = translateService.translateDiseaseToVietnamese(this.englishDisease);
    }
}
