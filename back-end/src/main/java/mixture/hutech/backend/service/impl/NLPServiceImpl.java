package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.repository.SymptomRepository;
import mixture.hutech.backend.service.NLPService;
import org.springframework.stereotype.Service;
import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NLPServiceImpl implements NLPService {
    private final VnCoreNLP vnCoreNLP;

    private final SymptomRepository symptomRepository;

    /**
     * Trích xuất triệu chứng từ văn bản tiếng Việt và dịch sang tiếng Anh
     * @param vietnameseSymptom Văn bản đầu vào bằng tiếng Việt
     * @return Danh sách các triệu chứng được xác định bằng tiếng Anh
     */
    @Override
    public List<String> extractSymptoms(String vietnameseSymptom) {
        try{
            Annotation annotation = new Annotation(vietnameseSymptom);
            vnCoreNLP.annotate(annotation);

            List<Word> words = annotation.getWords();

            String segmentedText = words.stream()
                    .map(Word::getForm)
                    .collect(Collectors.joining(" "));

            String[] symptomParts = segmentedText.split(" ");
            List<String> identifiedSymptoms = new ArrayList<>();
//            Set<String> knownVietnameseSymptoms = customTranslateService.getAllVietnameseSymptoms();

            Set<String> knownVietnameseSymptoms = symptomRepository.findAllSymptomVietnameseName().stream()
                    .map(SymptomResponse::getSymptomName)
                    .collect(Collectors.toSet());

            for (String symptomPart : symptomParts) {
                String normalizedText = symptomPart.toLowerCase().trim();

                for (String symptom : knownVietnameseSymptoms){
                    if(normalizedText.contains(symptom.toLowerCase())){
//                        String englishSymptom = String.valueOf(customTranslateService.translateSymptomsToEnglish(symptom));
                        String englishSymptom = String.valueOf(symptomRepository.findSymptomEnglishNameByVietnameseName(symptom));
                        if (englishSymptom != null){
                            identifiedSymptoms.add(englishSymptom);
                        }
                    }
                }
            }

            return identifiedSymptoms.stream()
                    .distinct()
                    .collect(Collectors.toList());

        }catch (Exception ex){
            throw new RuntimeException("Error extracting symptoms: " + ex.getMessage(), ex);
        }
    }


    /**
     * Định dạng danh sách triệu chứng cho đầu vào mô hình
     * @param symptoms chứng Danh sách các triệu chứng bằng tiếng Anh
     * @return Chuỗi triệu chứng được định dạng
     */
    @Override
    public String normalizeSymptoms(List<String> symptoms) {
        return symptoms.stream()
                .distinct()
                .collect(Collectors.joining(" "));
    }

    /**
     * Dịch dự đoán bệnh từ tiếng Anh sang tiếng Việt
     * @param englishDisease Tên bệnh bằng tiếng Anh
     * @return dịch tiếng Việt về dịch bệnh
     */
//    public String translateDiseaseToVietnamese(String englishDisease) {
//        return customTranslateService.translateDiseaseToVietnamese(englishDisease);
//    }
}
