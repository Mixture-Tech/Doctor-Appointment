package mixture.hutech.backend.client;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.enums.ErrorCodeEnum;
import mixture.hutech.backend.exceptions.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FlaskClient {
    private final RestTemplate restTemplate;
    private final Dotenv dotenv;

    public int predictDisease(List<Integer> symptoms) {
        Map<String, Object> request = new HashMap<>();
        request.put("symptoms", symptoms);
        String flaskApiUrl = dotenv.get("FLASK_API_URL") + "/predict";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, request, Map.class);
            return (int) response.getBody().get("predicted_disease");
        } catch (RestClientException e) {
            throw new ApiException(ErrorCodeEnum.EXTERNAL_SERVICE_ERROR);
        }
    }
}