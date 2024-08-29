package gastronomy.guide.model.propetries;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "token.signing")
public class JWTProperties {

    private String accessKey;
    private String refreshKey;
    private long accessExpirationTime;
    private long refreshExpirationTime;
}
