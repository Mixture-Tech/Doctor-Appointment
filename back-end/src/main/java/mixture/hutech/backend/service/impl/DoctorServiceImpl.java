package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.dto.response.DoctorResponse;
import mixture.hutech.backend.dto.response.DoctorScheduleResponse;
import mixture.hutech.backend.dto.response.DoctorSpecializationResponse;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import mixture.hutech.backend.repository.DoctorRepository;
import mixture.hutech.backend.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public List<DoctorResponse> listDoctor() {
        if(doctorRepository.findAll().isEmpty()){
            throw new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND);
        }
        return doctorRepository.findAllDoctor();
    }

    @Override
    public List<DoctorResponse> searchDoctorsByName(String name) {
        List<DoctorResponse> doctors = doctorRepository.searchDoctorsByName(name);
        if(doctors.isEmpty()) {
            throw new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND);
        }
        return doctors;
    }

    @Override
    public List<DoctorSpecializationResponse> listDoctorBySpecializationId(String specializationId) {
        List<DoctorSpecializationResponse> doctors = doctorRepository.listDoctorsBySpecializationId(specializationId);
        if(doctors.isEmpty()){
            throw new ApiException(ErrorCodeEnum.DOCTOR_NOT_FOUND);
        }

        List<DoctorScheduleResponse> schedules = doctorRepository.listDoctorScheduleBySpecializationId(specializationId);

        Map<String, List<DoctorScheduleResponse>> scheduleMap = schedules.stream()
                .collect(Collectors.groupingBy(DoctorScheduleResponse::getDoctorName));

        for(DoctorSpecializationResponse doctor : doctors){
            doctor.setSchedules(scheduleMap.getOrDefault(doctor.getDoctorName(), new ArrayList<>()));
        }

        return doctors;
    }
}
