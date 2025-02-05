package mixture.hutech.backend.repository;

import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
    @Query("SELECT new mixture.hutech.backend.dto.response.SymptomResponse(s.vietnameseSymptomName) FROM Symptom s")
    List<SymptomResponse> findAllSymptomVietnameseName();

    @Query("SELECT new mixture.hutech.backend.dto.response.SymptomResponse(s.englishSymptomName) FROM Symptom s")
    List<SymptomResponse> findAllSymptomEnglishName();

    @Query("SELECT s.englishSymptomName FROM Symptom s WHERE s.vietnameseSymptomName = ?1")
    String findSymptomEnglishNameByVietnameseName(String vietnameseSymptomName);
}
