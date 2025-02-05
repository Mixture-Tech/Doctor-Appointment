package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.repository.SymptomRepository;
import mixture.hutech.backend.service.SymptomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    private final SymptomRepository symptomRepository;

    @Override
    public List<SymptomResponse> findAllSymptomEnglishName() {
        return symptomRepository.findAllSymptomEnglishName();
    }

    @Override
    public List<SymptomResponse> findAllSymptomVietnameseName() {
        return symptomRepository.findAllSymptomVietnameseName();
    }
}
