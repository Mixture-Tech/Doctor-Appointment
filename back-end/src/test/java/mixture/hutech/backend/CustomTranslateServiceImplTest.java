//package mixture.hutech.backend;
//
//import mixture.hutech.backend.service.impl.CustomTranslateServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class CustomTranslateServiceImplTest {
//
//    private CustomTranslateServiceImpl translateService;
//
//    @BeforeEach
//    void setUp() {
//        translateService = new CustomTranslateServiceImpl();
//    }
//
//    @Test
//    void testTranslateSymptomsToEnglish_ExactMatch() {
//        // Test case 1: Kiểm tra dịch chính xác một triệu chứng
//        List<String> result1 = translateService.translateSymptomsToEnglish("ngứa");
//        assertFalse(result1.isEmpty());
//        assertEquals("itching", result1.get(0));
//
//        // Test case 2: Kiểm tra dịch nhiều triệu chứng trong một câu
//        List<String> result2 = translateService.translateSymptomsToEnglish("tôi bị ngứa và sốt cao");
//        assertTrue(result2.contains("itching"));
//        assertTrue(result2.contains("high_fever"));
//        assertEquals(2, result2.size());
//    }
//
//    @Test
//    void testTranslateSymptomsToEnglish_ApproximateMatch() {
//        // Test case với text gần giống
//        List<String> result = translateService.translateSymptomsToEnglish("ngứaa");
//        assertFalse(result.isEmpty());
//        assertEquals("itching", result.get(0));
//    }
//
//    @Test
//    void testTranslateSymptomsToEnglish_NoMatch() {
//        // Test case với text không tồn tại
//        List<String> result = translateService.translateSymptomsToEnglish("không có triệu chứng này");
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testTranslateDiseaseToVietnamese_ExactMatch() {
//        // Test case cho dịch chính xác bệnh
//        String result = translateService.translateDiseaseToVietnamese("Malaria");
//        assertEquals("sốt rét", result);
//    }
//
//    @Test
//    void testTranslateDiseaseToVietnamese_ApproximateMatch() {
//        // Test case cho dịch gần đúng
//        String result = translateService.translateDiseaseToVietnamese("Malariaa");
//        assertEquals("sốt rét", result);
//    }
//
//    @Test
//    void testTranslateDiseaseToVietnamese_NoMatch() {
//        // Test case cho bệnh không tồn tại
//        String result = translateService.translateDiseaseToVietnamese("NonExistentDisease");
//        assertEquals("Không thể nhận diện bệnh", result);
//    }
//
//    @Test
//    void testGetAllSymptoms() {
//        // Kiểm tra danh sách các triệu chứng
//        Set<String> vietnameseSymptoms = translateService.getAllVietnameseSymptoms();
//        Set<String> englishSymptoms = translateService.getAllEnglishSymptoms();
//
//        assertFalse(vietnameseSymptoms.isEmpty());
//        assertFalse(englishSymptoms.isEmpty());
//        assertEquals(vietnameseSymptoms.size(), englishSymptoms.size());
//    }
//
//    @Test
//    void testGetAllDiseases() {
//        // Kiểm tra danh sách các bệnh
//        Set<String> vietnameseDiseases = translateService.getAllVietnameseDiseases();
//        Set<String> englishDiseases = translateService.getAllEnglishDiseases();
//
//        assertFalse(vietnameseDiseases.isEmpty());
//        assertFalse(englishDiseases.isEmpty());
//        assertEquals(vietnameseDiseases.size(), englishDiseases.size());
//    }
//}