package mixture.hutech.backend.controller;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.entity.DoctorSchedule;
import mixture.hutech.backend.repository.DoctorScheduleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/doctor-schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {
    private final DoctorScheduleRepository doctorScheduleRepository;

    @GetMapping
    public List<DoctorSchedule> getAllDoctorSchedules() {
        return doctorScheduleRepository.findAll();
    }
}
