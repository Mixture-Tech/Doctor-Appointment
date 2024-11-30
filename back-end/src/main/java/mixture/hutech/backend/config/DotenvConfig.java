package mixture.hutech.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DotenvConfig implements EnvironmentPostProcessor {

    private Dotenv dotenv;

    @Bean
    public Dotenv dotenv() {
        if (dotenv == null) {
            dotenv = Dotenv.configure()
                    .directory("./")
                    .filename(".env")
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();
        }
        return dotenv;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        dotenv(); // Khởi tạo hoặc tái sử dụng bean Dotenv
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(entry -> dotenvMap.put(entry.getKey(), entry.getValue()));
        environment.getPropertySources().addLast(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}

