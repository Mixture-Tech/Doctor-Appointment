package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.request.SymptomRequest;
import mixture.hutech.backend.dto.response.DiseaseResponse;

public interface ChatbotService {
    DiseaseResponse predictDisease(SymptomRequest symptomRequest);
}
