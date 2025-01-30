package mixture.hutech.backend.service;

import mixture.hutech.backend.dto.response.SymptomResponse;

import java.util.List;

public interface SymptomService {
    List<SymptomResponse> findAllSymptomEnglishName();
    List<SymptomResponse> findAllSymptomVietnameseName();
}
