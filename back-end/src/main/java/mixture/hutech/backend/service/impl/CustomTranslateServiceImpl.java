//package mixture.hutech.backend.service.impl;
//
//import mixture.hutech.backend.config.TranslateConfiguration;
//import mixture.hutech.backend.service.CustomTranslateService;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//
//@Service
//public class CustomTranslateServiceImpl implements CustomTranslateService {
//
//    private Map<String, String> viToEnSymptoms = new HashMap<>();
//    private Map<String, String> enToViSymptoms = new HashMap<>();
//    private Map<String, String> viToEnDiseases = new HashMap<>();
//    private Map<String, String> enToViDiseases = new HashMap<>();
//    private Map<Integer, String> diseaseIdToNameMap = new HashMap<>();
//
//    public CustomTranslateServiceImpl() {
//        this.viToEnSymptoms = TranslateConfiguration.getViToEnSymptoms();
//        this.viToEnDiseases = TranslateConfiguration.getViToEnDiseases();
//        this.diseaseIdToNameMap = TranslateConfiguration.getDiseaseIdMapping();
//
//        // Tạo các map ngược
//        this.enToViSymptoms = new HashMap<>();
//        this.enToViDiseases = new HashMap<>();
//
//        // Khởi tạo các map ngược
//        viToEnSymptoms.forEach((vi, en) -> enToViSymptoms.put(en, vi));
//        viToEnDiseases.forEach((vi, en) -> enToViDiseases.put(en, vi));
//    }
//
//    @Override
//    public String translateDiseaseToVietnamese(String englishDiseases) {
//        String disease = englishDiseases.trim();
//        // Tìm kiếm chính xác
//        if (enToViDiseases.containsKey(disease)) {
//            return enToViDiseases.get(disease);
//        }
//
//        // Tìm kiếm tương đối
//        String bestMatch = null;
//        double bestSimilarity = 0.0;
//
//        for (Map.Entry<String, String> entry : enToViDiseases.entrySet()){
//            double similarity = calculateSimilarity(
//                    disease.toLowerCase(),
//                    entry.getKey().toLowerCase()
//            );
//            if(similarity > bestSimilarity){
//                bestSimilarity = similarity;
//                bestMatch = entry.getValue();
//            }
//        }
//
//        return bestMatch != null ? bestMatch : "Không thể nhận diện bệnh";
//    }
//
//    private double calculateSimilarity(String s1, String s2) {
//        int edits = levenshteinDistance(s1, s2);
//        int maxLength = Math.max(s1.length(), s2.length());
//        return 1.0 - (double) edits / maxLength;
//    }
//
//    private int levenshteinDistance(String s1, String s2) {
//        int m = s1.length();
//        int n = s2.length();
//        int[][] dp = new int[m + 1][n + 1];
//
//        for (int i = 0; i <= s1.length(); i++) {
//            for (int j = 0; j <= s2.length(); j++) {
//                if (i == 0) {
//                    dp[i][j] = j;
//                } else if (j == 0) {
//                    dp[i][j] = i;
//                } else {
//                    dp[i][j] = min(
//                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
//                            dp[i - 1][j] + 1,
//                            dp[i][j - 1] + 1
//                    );
//                }
//            }
//        }
//
//        return dp[m][n];
//    }
//
//    private int min(int... numbers) {
//        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
//    }
//
//    // Helper methods để lấy danh sách các triệu chứng và bệnh
//    @Override
//    public Set<String> getAllVietnameseDiseases() {
//        return viToEnDiseases.keySet();
//    }
//
//    public Set<String> getAllEnglishDiseases() {
//        return enToViDiseases.keySet();
//    }
//
//    // Phương thức mới để lấy tên bệnh từ ID
//    @Override
//    public String getDiseaseNameById(int diseaseId) {
//        return diseaseIdToNameMap.getOrDefault(diseaseId, "Unknown Disease");
//    }
//
//    @Override
//    public List<String> getOrderedEnglishSymptoms() {
//        // Danh sách triệu chứng theo thứ tự từ 1-132
//        List<String> orderedSymptoms = Arrays.asList(
//                "itching", "skin_rash", "nodal_skin_eruptions", "continuous_sneezing",
//                "shivering", "chills", "joint_pain", "stomach_pain", "acidity",
//                "ulcers_on_tongue", "muscle_wasting", "vomiting", "burning_micturition",
//                "spotting_urination", "fatigue", "weight_gain", "anxiety",
//                "cold_hands_and_feets", "mood_swings", "weight_loss", "restlessness",
//                "lethargy", "patches_in_throat", "irregular_sugar_level", "cough",
//                "high_fever", "sunken_eyes", "breathlessness", "sweating", "dehydration",
//                "indigestion", "headache", "yellowish_skin", "dark_urine", "nausea",
//                "loss_of_appetite", "pain_behind_the_eyes", "back_pain", "constipation",
//                "abdominal_pain", "diarrhoea", "mild_fever", "yellow_urine",
//                "yellowing_of_eyes", "acute_liver_failure", "fluid_overload",
//                "swelling_of_stomach", "swelled_lymph_nodes", "malaise",
//                "blurred_and_distorted_vision", "phlegm", "throat_irritation",
//                "redness_of_eyes", "sinus_pressure", "runny_nose", "congestion",
//                "chest_pain", "weakness_in_limbs", "fast_heart_rate",
//                "pain_during_bowel_movements", "pain_in_anal_region", "bloody_stool",
//                "irritation_in_anus", "neck_pain", "dizziness", "cramps", "bruising",
//                "obesity", "swollen_legs", "swollen_blood_vessels", "puffy_face_and_eyes",
//                "enlarged_thyroid", "brittle_nails", "swollen_extremeties",
//                "excessive_hunger", "extra_marital_contacts", "drying_and_tingling_lips",
//                "slurred_speech", "knee_pain", "hip_joint_pain", "muscle_weakness",
//                "stiff_neck", "swelling_joints", "movement_stiffness",
//                "spinning_movements", "loss_of_balance", "unsteadiness",
//                "weakness_of_one_body_side", "loss_of_smell", "bladder_discomfort",
//                "foul_smell_of_urine", "continuous_feel_of_urine", "passage_of_gases",
//                "internal_itching", "toxic_look_(typhos)", "depression", "irritability",
//                "muscle_pain", "altered_sensorium", "red_spots_over_body", "belly_pain",
//                "abnormal_menstruation", "dischromic_patches", "watering_from_eyes",
//                "increased_appetite", "polyuria", "family_history", "mucoid_sputum",
//                "rusty_sputum", "lack_of_concentration", "visual_disturbances",
//                "receiving_blood_transfusion", "receiving_unsterile_injections", "coma",
//                "stomach_bleeding", "distention_of_abdomen",
//                "history_of_alcohol_consumption", "fluid_overload.1", "blood_in_sputum",
//                "prominent_veins_on_calf", "palpitations", "painful_walking",
//                "pus_filled_pimples", "blackheads", "scurring", "skin_peeling",
//                "silver_like_dusting", "small_dents_in_nails", "inflammatory_nails",
//                "blister", "red_sore_around_nose", "yellow_crust_ooze"
//        );
//        return orderedSymptoms;
//    }
//
//}
