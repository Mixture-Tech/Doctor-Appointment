package mixture.hutech.backend.repository;

import mixture.hutech.backend.dto.response.DiseaseResponse;
import mixture.hutech.backend.dto.response.SymptomResponse;
import mixture.hutech.backend.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Integer> {

    @Query("SELECT new mixture.hutech.backend.dto.response.DiseaseResponse(d.id, d.diseaseVietnameseName) FROM Disease d WHERE d.specialization.id = :specializationId")
    List<DiseaseResponse> listDiseaseBySpecializationId(String specializationId);


    @Query("SELECT new mixture.hutech.backend.dto.response.DiseaseResponse(d.id, d.diseaseEnglishName) FROM Disease d")
    List<DiseaseResponse> findAllDiseaseEnglishName();

    @Query("SELECT d.diseaseEnglishName FROM Disease d WHERE d.id = ?1")
    String getDiseaseEnglishNameById(int diseaseId);

    @Query("SELECT d.diseaseVietnameseName FROM Disease d WHERE d.diseaseEnglishName = ?1")
    String findDiseaseByVietnameseNameByEnglishName(String englishDisease);
}
