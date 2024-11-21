package mixture.hutech.backend.service.impl;

import mixture.hutech.backend.service.CustomTranslateService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CustomTranslateServiceImpl implements CustomTranslateService {

    private final Map<String, String> viToEnSymptoms = new HashMap<>();
    private final Map<String, String> enToViSymptoms = new HashMap<>();
    private final Map<String, String> viToEnDiseases = new HashMap<>();
    private final Map<String, String> enToViDiseases = new HashMap<>();
    private final Map<Integer, String> diseaseIdToNameMap = new HashMap<>();

    public CustomTranslateServiceImpl() {
        initializeSymptomsDictionary();
        initializeDiseasesDictionary();
        initializeDiseaseIdMapping();
    }

    private void initializeDiseaseIdMapping() {
        // Ánh xạ ID bệnh với tên bệnh tiếng Anh
        diseaseIdToNameMap.put(0, "Drug Reaction");
        diseaseIdToNameMap.put(1, "Malaria");
        diseaseIdToNameMap.put(2, "Allergy");
        diseaseIdToNameMap.put(3, "Hypothyroidism");
        diseaseIdToNameMap.put(4, "Psoriasis");
        diseaseIdToNameMap.put(5, "GERD");
        diseaseIdToNameMap.put(6, "Chronic cholestasis");
        diseaseIdToNameMap.put(7, "Hepatitis A");
        // Tiếp tục thêm các bệnh khác theo thứ tự ID
        // ...
        diseaseIdToNameMap.put(29, "Hepatitis D");
        // Thêm các bệnh còn lại
    }

    private void initializeSymptomsDictionary() {
        // Mapping tiếng Việt - tiếng Anh cho các triệu chứng
        viToEnSymptoms.put("ngứa", "itching");
        viToEnSymptoms.put("phát ban", "skin_rash");
        viToEnSymptoms.put("nổi mẩn da", "nodal_skin_eruptions");
        viToEnSymptoms.put("hắt hơi liên tục", "continuous_sneezing");
        viToEnSymptoms.put("run rẩy", "shivering");
        viToEnSymptoms.put("ớn lạnh", "chills");
        viToEnSymptoms.put("đau khớp", "joint_pain");
        viToEnSymptoms.put("đau dạ dày", "stomach_pain");
        viToEnSymptoms.put("axit dạ dày", "acidity");
        viToEnSymptoms.put("loét lưỡi", "ulcers_on_tongue");
        viToEnSymptoms.put("teo cơ", "muscle_wasting");
        viToEnSymptoms.put("nôn", "vomiting");
        viToEnSymptoms.put("tiểu buốt", "burning_micturition");
        viToEnSymptoms.put("tiểu lắt nhắt", "spotting_urination");
        viToEnSymptoms.put("mệt mỏi", "fatigue");
        viToEnSymptoms.put("tăng cân", "weight_gain");
        viToEnSymptoms.put("lo âu", "anxiety");
        viToEnSymptoms.put("tay chân lạnh", "cold_hands_and_feets");
        viToEnSymptoms.put("thay đổi tâm trạng", "mood_swings");
        viToEnSymptoms.put("sụt cân", "weight_loss");
        viToEnSymptoms.put("bồn chồn", "restlessness");
        viToEnSymptoms.put("uể oải", "lethargy");
        viToEnSymptoms.put("đốm trắng trong cổ họng", "patches_in_throat");
        viToEnSymptoms.put("đường huyết không đều", "irregular_sugar_level");
        viToEnSymptoms.put("ho", "cough");
        viToEnSymptoms.put("sốt cao", "high_fever");
        viToEnSymptoms.put("mắt trũng", "sunken_eyes");
        viToEnSymptoms.put("khó thở", "breathlessness");
        viToEnSymptoms.put("đổ mồ hôi", "sweating");
        viToEnSymptoms.put("mất nước", "dehydration");
        viToEnSymptoms.put("khó tiêu", "indigestion");
        viToEnSymptoms.put("nhức đầu", "headache");
        viToEnSymptoms.put("da vàng", "yellowish_skin");
        viToEnSymptoms.put("nước tiểu sẫm màu", "dark_urine");
        viToEnSymptoms.put("buồn nôn", "nausea");
        viToEnSymptoms.put("chán ăn", "loss_of_appetite");
        viToEnSymptoms.put("đau sau mắt", "pain_behind_the_eyes");
        viToEnSymptoms.put("đau lưng", "back_pain");
        viToEnSymptoms.put("táo bón", "constipation");
        viToEnSymptoms.put("đau bụng", "abdominal_pain");
        viToEnSymptoms.put("tiêu chảy", "diarrhoea");
        // ... thêm các triệu chứng khác tương tự

        // Tạo mapping ngược
        viToEnSymptoms.forEach((vi, en) -> enToViSymptoms.put(en, vi));
    }

    private void initializeDiseasesDictionary() {
        // Mapping tiếng Việt - tiếng Anh cho các bệnh
        viToEnDiseases.put("phản ứng thuốc", "Drug Reaction");
        viToEnDiseases.put("sốt rét", "Malaria");
        viToEnDiseases.put("dị ứng", "Allergy");
        viToEnDiseases.put("suy giáp", "Hypothyroidism");
        viToEnDiseases.put("vẩy nến", "Psoriasis");
        viToEnDiseases.put("trào ngược dạ dày", "GERD");
        viToEnDiseases.put("ứ mật mãn tính", "Chronic cholestasis");
        viToEnDiseases.put("viêm gan A", "Hepatitis A");
        viToEnDiseases.put("thoái hóa khớp", "Osteoarthristis");
        viToEnDiseases.put("chóng mặt tư thế", "Paroymsal Positional Vertigo");
        viToEnDiseases.put("hạ đường huyết", "Hypoglycemia");
        viToEnDiseases.put("mụn trứng cá", "Acne");
        viToEnDiseases.put("tiểu đường", "Diabetes");
        viToEnDiseases.put("chốc lở", "Impetigo");
        viToEnDiseases.put("cao huyết áp", "Hypertension");
        viToEnDiseases.put("loét dạ dày", "Peptic ulcer diseae");
        viToEnDiseases.put("trĩ", "Dimorphic hemorrhoids(piles)");
        viToEnDiseases.put("cảm lạnh thông thường", "Common Cold");
        viToEnDiseases.put("thủy đậu", "Chicken pox");
        viToEnDiseases.put("thoái hóa cột sống cổ", "Cervical spondylosis");
        // ... thêm các bệnh khác tương tự

        // Tạo mapping ngược
        viToEnDiseases.forEach((vi, en) -> enToViDiseases.put(en, vi));
    }


    @Override
    public List<String> translateSymptomsToEnglish(String vietnameseSymptoms) {
        List<String> englishSymptoms = new ArrayList<>();
        String lowerText = vietnameseSymptoms.toLowerCase().trim();

        // Ưu tiên tìm kiếm chính xác
        for(Map.Entry<String, String> entry : viToEnSymptoms.entrySet()) {
            if(lowerText.contains(entry.getKey().toLowerCase())) {
                englishSymptoms.add(entry.getValue());
            }
        }

        // Nếu không tìm thấy, thử tìm kiếm tương đối
        if(englishSymptoms.isEmpty()){
            for(Map.Entry<String, String> entry : viToEnSymptoms.entrySet()) {
                if(calculateSimilarity(lowerText, entry.getKey().toLowerCase()) > 0.7) {
                    englishSymptoms.add(entry.getValue());
                }
            }
        }

        return englishSymptoms;
    }

    @Override
    public String translateDiseaseToVietnamese(String englishDiseases) {
        String disease = englishDiseases.trim();
        // Tìm kiếm chính xác
        if (enToViDiseases.containsKey(disease)) {
            return enToViDiseases.get(disease);
        }

        // Tìm kiếm tương đối
        String bestMatch = null;
        double bestSimilarity = 0.0;

        for (Map.Entry<String, String> entry : enToViDiseases.entrySet()){
            double similarity = calculateSimilarity(
                    disease.toLowerCase(),
                    entry.getKey().toLowerCase()
            );
            if(similarity > bestSimilarity){
                bestSimilarity = similarity;
                bestMatch = entry.getValue();
            }
        }

        return bestMatch != null ? bestMatch : "Không thể nhận diện bệnh";
    }

    private double calculateSimilarity(String s1, String s2) {
        int edits = levenshteinDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1.0 - (double) edits / maxLength;
    }

    private int levenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    );
                }
            }
        }

        return dp[m][n];
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    // Helper methods để lấy danh sách các triệu chứng và bệnh
    @Override
    public Set<String> getAllVietnameseSymptoms() {
        return viToEnSymptoms.keySet();
    }

    @Override
    public Set<String> getAllEnglishSymptoms() {
        return enToViSymptoms.keySet();
    }

    @Override
    public Set<String> getAllVietnameseDiseases() {
        return viToEnDiseases.keySet();
    }

    public Set<String> getAllEnglishDiseases() {
        return enToViDiseases.keySet();
    }

    // Phương thức mới để lấy tên bệnh từ ID
    @Override
    public String getDiseaseNameById(int diseaseId) {
        return diseaseIdToNameMap.getOrDefault(diseaseId, "Unknown Disease");
    }

}
