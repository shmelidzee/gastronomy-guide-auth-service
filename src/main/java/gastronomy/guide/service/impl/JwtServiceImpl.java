package gastronomy.guide.service.impl;

import gastronomy.guide.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.access-key}")
    private String jwtSigningKey;

    @Value("${token.signing.refresh-key}")
    private String jwtRefreshKey;

    @Value("${token.signing.access-expiration-time}")
    private long jwtExpiration;

    @Value("${token.signing.refresh-expiration-time}")
    private long jwtRefreshExpiration;

    @Override
    public String extractUsername(String token, String key) {
        key = key == null ? jwtSigningKey : key;
        return extractClaim(token, Claims::getSubject, key);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), jwtSigningKey, userDetails, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), jwtRefreshKey, userDetails, jwtRefreshExpiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, boolean isRefreshToken) {
        String key = isRefreshToken ? jwtRefreshKey : jwtSigningKey;
        final String username = extractUsername(token, key);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, key);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            String signingKey,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(signingKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token, String key) {
        return extractExpiration(token, key).before(new Date());
    }

    private Date extractExpiration(String token, String key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    private Claims extractAllClaims(String token, String key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(String signInKey) {
        byte[] keyBytes = Decoders.BASE64.decode(signInKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
