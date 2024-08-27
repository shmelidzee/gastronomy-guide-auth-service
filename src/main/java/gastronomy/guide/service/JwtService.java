package gastronomy.guide.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    boolean isTokenValid(String token, UserDetails userDetails);
}
