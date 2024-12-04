package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.client.FlaskClient;
import mixture.hutech.backend.dto.request.SymptomRequest;
import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.SpecializationResponse;
import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.DiseaseRepository;
import mixture.hutech.backend.repository.SpecializationRepository;
import mixture.hutech.backend.repository.SymptomRepository;
import mixture.hutech.backend.service.ChatbotService;
import mixture.hutech.backend.service.NLPService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final NLPService nlpService;
    private final FlaskClient flaskClient;
    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public DiseaseResponse predictDisease(SymptomRequest symptomRequest) {
        // 1. Process input symptoms
        List<String> extractedSymptoms = processSymptoms(symptomRequest);

        // 2. Prepare request for Flask API
        List<Integer> symptomBinaryArray = prepareSymptomBinaryArray(extractedSymptoms);

        // 3. Call Flask API
        int predictedDiseaseId = flaskClient.predictDisease(symptomBinaryArray);

        // 4. Create disease response
        return createDiseaseResponse(predictedDiseaseId, extractedSymptoms);
    }

    // Rest of the methods remain the same as in the previous implementation
    private List<String> processSymptoms(SymptomRequest symptomRequest) {
        List<String> extractedSymptoms = nlpService.extractSymptoms(symptomRequest.getSymptomName());

        if (extractedSymptoms.isEmpty()) {
            throw new ApiException(ErrorCodeEnum.SYMPTOM_NOT_FOUND);
        }

        return extractedSymptoms;
    }

    private List<Integer> prepareSymptomBinaryArray(List<String> extractedSymptoms) {
        // Normalize symptoms
        String normalizedSymptoms = nlpService.normalizeSymptoms(extractedSymptoms);

        // Convert to binary array
        List<Integer> binaryArray = new ArrayList<>(Collections.nCopies(132, 0));
        List<String> orderedSymptoms = symptomRepository.findAllSymptomEnglishName().stream()
                .map(SymptomResponse::getSymptomName)
                .toList();

        List<String> symptomsList = Arrays.stream(normalizedSymptoms.split(" "))
                .map(symptom -> symptom.replaceAll("[\\[\\]]", ""))
                .toList();

        for (String symptom : symptomsList) {
            int index = orderedSymptoms.indexOf(symptom);
            if (index != -1) {
                binaryArray.set(index, 1);
            }
        }
        return binaryArray;
    }

    private DiseaseResponse createDiseaseResponse(int diseaseId, List<String> extractedSymptoms) {
        // Fetch disease details from repository
        String englishDisease = diseaseRepository.getDiseaseEnglishNameById(diseaseId);
        String vietnameseDisease = diseaseRepository.findDiseaseByVietnameseNameByEnglishName(englishDisease);
        SpecializationResponse specializationResponse = specializationRepository.findSpecializationByDiseaseId(diseaseId);

        return DiseaseResponse.builder()
                .diseaseId(diseaseId)
                .englishDisease(englishDisease)
                .vietnameseDisease(vietnameseDisease)
                .extractedSymptoms(extractedSymptoms)
                .specialization(specializationResponse)
                .build();
    }
}