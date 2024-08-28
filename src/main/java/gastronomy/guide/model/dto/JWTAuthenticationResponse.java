package gastronomy.guide.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
