package mixture.hutech.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import vn.pipeline.VnCoreNLP;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class NLPConfig {

    @Bean
    @Primary
    public VnCoreNLP vnCoreNLP(){
        try {
            String[] annotators = {"wseg"};
            return new VnCoreNLP(annotators);
        }catch (IOException ex){
            throw new RuntimeException("Could not initialize VnCoreNLP", ex);
        }
    }
}
