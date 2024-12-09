package mixture.hutech.backend.service.impl;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.service.ProvinceGHNService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProvinceGHNServiceImpl implements ProvinceGHNService {
    private final Dotenv dotenv;
    private final RestTemplate restTemplate;

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", dotenv.get("TOKEN_API_PROVINCE_GHN"));
        return headers;
    }

    @Override
    public List<Map<String, Object>> getProvinces() {
        String baseUrl = dotenv.get("BASE_URL_API_PROVINCE_GHN");
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/province",
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return (List<Map<String, Object>>) responseBody.get("data");
        } else {
            throw new RuntimeException("Failed to load provinces");
        }
    }

    @Override
    public List<Map<String, Object>> getDistricts(int provinceId) {
        String baseUrl = dotenv.get("BASE_URL_API_PROVINCE_GHN");
        HttpHeaders headers = createHeaders();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("province_id", provinceId);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/district",
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return (List<Map<String, Object>>) responseBody.get("data");
        } else {
            throw new RuntimeException("Failed to load districts");
        }
    }

    @Override
    public List<Map<String, Object>> getWards(int districtId) {
        String baseUrl = dotenv.get("BASE_URL_API_PROVINCE_GHN");
        HttpHeaders headers = createHeaders();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("district_id", districtId);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                baseUrl + "/ward",
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return (List<Map<String, Object>>) responseBody.get("data");
        } else {
            throw new RuntimeException("Failed to load wards");
        }
    }
}