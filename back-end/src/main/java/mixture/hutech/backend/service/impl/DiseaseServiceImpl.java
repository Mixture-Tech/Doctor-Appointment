package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.SpecializationResponse;
import mixture.hutech.backend.entity.Disease;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.DiseaseRepository;
import mixture.hutech.backend.service.DiseaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    @Override
    public List<DiseaseResponse> listDiseaseBySpecializationId(String specializationId) {
        List<DiseaseResponse> diseases = diseaseRepository.listDiseaseBySpecializationId(specializationId);
        if(diseases.isEmpty()){
            throw new ApiException(ErrorCodeEnum.DISEASE_NOT_FOUND);
        }
        return diseases;
    }

    @Override
    public List<DiseaseResponse> findAllDiseaseEnglishName() {
        return diseaseRepository.findAllDiseaseEnglishName();
    }

//    public DiseaseResponse getDiseaseResponse(int diseaseId, List<String> extractedSymptoms) {
//        Disease disease = diseaseRepository.findById(diseaseId)
//                .orElseThrow(() -> new ApiException(ErrorCodeEnum.DISEASE_NOT_FOUND));
//
//        String englishDisease = diseaseRepository.getDiseaseEnglishNameById(diseaseId);
//        String vietnameseDisease = diseaseRepository.findDiseaseByVietnameseNameByEnglishName(englishDisease);
//
//        SpecializationResponse specializationResponse = null;
//        if (disease.getSpecialization() != null) {
//            specializationResponse = new SpecializationResponse(
//                    disease.getSpecialization().getId(),
//                    disease.getSpecialization().getSpecializationName(),
//                    disease.getSpecialization().getImage()
//            );
//        }
//
//        return new DiseaseResponse(diseaseId, englishDisease, vietnameseDisease, extractedSymptoms, specializationResponse);
//    }
}
