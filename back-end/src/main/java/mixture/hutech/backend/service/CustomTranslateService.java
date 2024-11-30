package mixture.hutech.backend.service;

import java.util.List;
import java.util.Set;

public interface CustomTranslateService {
    List<String> translateSymptomsToEnglish(String vietnameseSymptoms);
    String translateDiseaseToVietnamese(String englishDiseases);
    Set<String> getAllVietnameseSymptoms();
    Set<String> getAllVietnameseDiseases();
    Set<String> getAllEnglishSymptoms();
    String getDiseaseNameById(int diseaseId);
    List<String> getOrderedEnglishSymptoms();
}
