package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.SymptomRequest;
import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.CustomTranslateService;
import mixture.hutech.backend.service.NLPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chatbot")
public class ChatbotController {
//    @Value("${API_PREDICT_URL}")
//    private String flaskApiUrl;

    private final RestTemplate restTemplate;
    private final NLPService nlpService;
    private final CustomTranslateService translateService;

    @PostMapping("/predict-disease")
    public ResponseEntity<MessageResponse> predictDisease(@RequestBody SymptomRequest symptomRequest) {
        try{
            // 1. Xử lý các triệu chứng nhập vào bằng NLP
            List<String> extractedSymptoms = nlpService.extractSymptoms(symptomRequest.getSymptomName());

            // Kiểm tra nếu không tìm thấy triệu chứng
            if(extractedSymptoms.isEmpty()){
                return ResponseEntity
                        .status(ErrorCodeEnum.SYMPTOM_NOT_FOUND.getHttpStatus())
                        .body(MessageResponse.builder()
                                .errorCode(ErrorCodeEnum.SYMPTOM_NOT_FOUND)
                                .message(ErrorCodeEnum.SYMPTOM_NOT_FOUND.getMessage())
                                .build());
            }

            // 2. Chuẩn hóa triệu chứng
            String normalizedSymptoms = nlpService.normalizeSymptoms(extractedSymptoms);

            // 3. Chuẩn bị request cho Flask API
            Map<String, Object> flaskRequest = new HashMap<>();
            flaskRequest.put("symptoms", convertSymptomsToBinaryArray(normalizedSymptoms));

            // 4. Gọi Flask API
            ResponseEntity<Map> flaskResponse = restTemplate.postForEntity(
                    "http://127.0.0.1:5000/predict",
                    flaskRequest,
                    Map.class
            );

            // 5. Lấy ID bệnh từ response
            int diseaseId = (int) flaskResponse.getBody().get("predicted_disease");

            // 6. Tạo response với thông tin bệnh
            DiseaseResponse diseaseResponse = new DiseaseResponse(
                    diseaseId,
                    extractedSymptoms,
                    translateService
                    );
            return ResponseEntity.ok(MessageResponse.builder()
                    .errorCode(ErrorCodeEnum.OK)
                    .message(ErrorCodeEnum.OK.getMessage())
                    .data(diseaseResponse)
                    .build());
        }catch (ApiException e) {
            return ResponseEntity
                    .status(e.getErrorCodeEnum().getHttpStatus())
                    .body(MessageResponse.builder()
                            .errorCode(e.getErrorCodeEnum())
                            .message(e.getMessage())
                            .build());
        }

    }

    private List<Integer> convertSymptomsToBinaryArray(String normalizedSymptoms) {
        List<Integer> binaryArray = new ArrayList<>(Collections.nCopies(132, 0));

        Set<String> allSymptoms = translateService.getAllEnglishSymptoms();
        String[] symptomsList = normalizedSymptoms.split(" ");

        for (String symptom : symptomsList){
            int index = Arrays.asList(allSymptoms.toArray()).indexOf(symptom);
            if(index != -1){
                binaryArray.set(index, 1);
            }
        }
        return binaryArray;
    }

}
