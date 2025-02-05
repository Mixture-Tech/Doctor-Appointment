package mixture.hutech.backend.service;

import java.util.List;
import java.util.Map;

public interface ProvinceGHNService {
    List<Map<String, Object>> getProvinces();
    List<Map<String, Object>> getDistricts(int provinceId);
    List<Map<String, Object>> getWards(int districtId);
}
