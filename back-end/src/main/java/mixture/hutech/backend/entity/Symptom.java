package mixture.hutech.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Symptom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "eng_symptom")
    private String englishSymptomName;

    @Column(name = "vie_symptom")
    private String vietnameseSymptomName;

    @Column(name = "symptom_description")
    private String symptomDescription;
}
