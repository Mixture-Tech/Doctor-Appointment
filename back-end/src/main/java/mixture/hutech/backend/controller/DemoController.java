package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.service.DiseaseService;
import mixture.hutech.backend.service.SymptomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/demo-controller")
@RequiredArgsConstructor
public class DemoController {

    private final SymptomService symptomService;
    private final DiseaseService diseaseService;

    @GetMapping
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok(diseaseService.findAllDiseaseEnglishName());
    }
}
