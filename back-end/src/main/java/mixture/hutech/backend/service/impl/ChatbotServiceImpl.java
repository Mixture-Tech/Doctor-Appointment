package mixture.hutech.backend.service.impl;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import mixture.hutech.backend.dto.request.SymptomRequest;
import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.DiseaseRepository;
import mixture.hutech.backend.repository.SymptomRepository;
import mixture.hutech.backend.service.ChatbotService;
import mixture.hutech.backend.service.NLPService;
import org.hibernate.cfg.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final Dotenv dotenv;
    private final NLPService nlpService;
    private final RestTemplate restTemplate;
    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;

    @Override
    public DiseaseResponse predictDisease(SymptomRequest symptomRequest) {
        // 1. Xử lý các triệu chứng nhập vào bằng NLP
        List<String> extractedSymptoms = nlpService.extractSymptoms(symptomRequest.getSymptomName());

        // Kiểm tra nếu không tìm thấy triệu chứng
        if(extractedSymptoms.isEmpty()) {
            throw new ApiException(ErrorCodeEnum.SYMPTOM_NOT_FOUND);
        }

        // 2. Chuẩn hóa triệu chứng
        String normalizedSymptoms = nlpService.normalizeSymptoms(extractedSymptoms);

        // 3. Chuẩn bị request cho Flask API
        Map<String, Object> flaskRequest = new HashMap<>();
        flaskRequest.put("symptoms", convertSymptomsToBinaryArray(normalizedSymptoms));

        // 4. Gọi Flask API
        String flaskApiUrl = dotenv.get("FLASK_API_URL");
        ResponseEntity<Map> flaskResponse;
        try {
            flaskResponse = restTemplate.postForEntity(
                    flaskApiUrl + "/predict",
                    flaskRequest,
                    Map.class
            );
        } catch (RestClientException e) {
            throw new ApiException(ErrorCodeEnum.EXTERNAL_SERVICE_ERROR);
        }

        // 5. Lấy ID bệnh từ response
        int diseaseId = (int) flaskResponse.getBody().get("predicted_disease");

        // 6. Tạo response với thông tin bệnh
        return new DiseaseResponse(
                diseaseId,
                extractedSymptoms,
                diseaseRepository
        );
    }

    private List<Integer> convertSymptomsToBinaryArray(String normalizedSymptoms) {
        List<Integer> binaryArray = new ArrayList<>(Collections.nCopies(132, 0));
        List<String> orderedSymptoms = symptomRepository.findAllSymptomEnglishName().stream()
                .map(SymptomResponse::getSymptomName)
                .toList();

        List<String> symptomsList = Arrays.stream(normalizedSymptoms.split(" "))
                .map(symptom -> symptom.replaceAll("[\\[\\]]", ""))
                .toList();

        for (String symptom : symptomsList) {
            int index = orderedSymptoms.indexOf(symptom);
            if(index != -1) {
                binaryArray.set(index, 1);
            }
        }
        return binaryArray;
    }
}
