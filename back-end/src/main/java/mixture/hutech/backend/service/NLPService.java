package mixture.hutech.backend.service;

import java.util.List;

public interface NLPService {
    List<String> extractSymptoms(String symptomName);
    String normalizeSymptoms(List<String> symptoms);
}
