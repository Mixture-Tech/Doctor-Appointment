package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.request.SymptomRequest;
import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.MessageResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.service.ChatbotService;
//import mixture.hutech.backend.service.CustomTranslateService;
import mixture.hutech.backend.service.NLPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/chatbot")
public class ChatbotController { ;
    private final ChatbotService chatbotService;

    @PostMapping("/predict-disease")
    public ResponseEntity<MessageResponse> predictDisease(@RequestBody SymptomRequest symptomRequest) {
        try{
            DiseaseResponse diseaseResponse = chatbotService.predictDisease(symptomRequest);

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
}
