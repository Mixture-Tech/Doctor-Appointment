package mixture.hutech.backend.service.impl;

import lombok.RequiredArgsConstructor;
import mixture.hutech.backend.service.LoggingService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void logChatbotInteraction(String userId, String symptoms, String disease) {
        String key = "chatbot:interactions:" + userId;
        Map<String, Object> interaction = new HashMap<>();
        interaction.put("timestamp", System.currentTimeMillis());
        interaction.put("symptoms", symptoms);
        interaction.put("disease", disease);

        redisTemplate.opsForHash().putAll(key, interaction);
    }
}
