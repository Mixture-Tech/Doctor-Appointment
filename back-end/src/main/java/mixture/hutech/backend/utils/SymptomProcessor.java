//package mixture.hutech.backend.utils;
//
//import lombok.RequiredArgsConstructor;
//import mixture.hutech.backend.dto.response.SymptomResponse;
//import mixture.hutech.backend.enums.ErrorCodeEnum;
//import mixture.hutech.backend.exceptions.ApiException;
//import mixture.hutech.backend.repository.SymptomRepository;
//import mixture.hutech.backend.service.NLPService;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class SymptomProcessor {
//
//    private final NLPService nlpService;
//    private final SymptomRepository symptomRepository;
//
//    public List<String> extractAndNormalize(String symptomInput) {
//        List<String> extractedSymptoms = nlpService.extractSymptoms(symptomInput);
//
//        if (extractedSymptoms.isEmpty()) {
//            throw new ApiException(ErrorCodeEnum.SYMPTOM_NOT_FOUND);
//        }
//
////        return nlpService.normalizeSymptoms(extractedSymptoms).stream()
////                .map(symptom -> symptom.replaceAll("[\\[\\]]", "")) // Chuẩn hóa
////                .toList();
//    }
//
//    public List<Integer> convertToBinaryArray(List<String> symptoms) {
//        List<Integer> binaryArray = new ArrayList<>(Collections.nCopies(132, 0));
//        List<String> orderedSymptoms = symptomRepository.findAllSymptomEnglishName().stream()
//                .map(SymptomResponse::getSymptomName)
//                .toList();
//
//        for (String symptom : symptoms) {
//            int index = orderedSymptoms.indexOf(symptom);
//            if (index != -1) {
//                binaryArray.set(index, 1);
//            }
//        }
//
//        return binaryArray;
//    }
//}
//
