package gastronomy.guide.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUsername(String token, String key);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String key);

    boolean isTokenValid(String token, UserDetails userDetails, boolean isRefreshToken);
}
