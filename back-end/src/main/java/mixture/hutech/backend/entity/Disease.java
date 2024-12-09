package mixture.hutech.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "disease_eng_name")
    private String diseaseEnglishName;

    @Column(name = "disease_vie_name")
    private String diseaseVietnameseName;

    @Column(name = "cause_of_disease", length = 1000)
    private String causeOfDisease;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;
}
