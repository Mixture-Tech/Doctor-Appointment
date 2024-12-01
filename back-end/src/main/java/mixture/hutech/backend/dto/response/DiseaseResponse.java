package mixture.hutech.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
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
    private List<String> extractedSymptoms;

    public DiseaseResponse(int diseaseId, String vietnameseDisease){
        this.diseaseId = diseaseId;
        this.vietnameseDisease = vietnameseDisease;
    }

    public DiseaseResponse(int disease_Id, List<String> extractedSymptoms, DiseaseRepository diseaseRepository) {
        this.diseaseId = disease_Id;
        this.extractedSymptoms = extractedSymptoms;
        this.englishDisease = diseaseRepository.getDiseaseEnglishNameById(disease_Id);
        this.vietnameseDisease = diseaseRepository.findDiseaseByVietnameseNameByEnglishName(this.englishDisease);
    }
}
