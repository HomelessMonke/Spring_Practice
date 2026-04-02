package homeless.monkey.com.security_task2.utils;

import homeless.monkey.com.security_task2.security.filter.JwtAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    private SecretKey secretKey;

    private static final long EXPIRATION_TIME = 3600000;
    private static final Logger logger = LoggerFactory.getLogger("JWT");

    public JwtUtils(){
        String secretString = "it's-my-super-puper-mega-ultra-secret1?!";
        byte[] keyBytes = secretString.getBytes(StandardCharsets.UTF_8);
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        String token = Jwts.builder()
                .claims(Map.of("roles", userDetails.getAuthorities()))
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();

        logger.info("Generating access_token for user: {}", userDetails.getUsername());
        return token;
    }

    public String generateRefreshToken(UserDetails userDetails){
        String token = Jwts.builder()
                .claims(Map.of("roles", userDetails.getAuthorities()))
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 24))
                .signWith(secretKey)
                .compact();

        logger.info("Generating refresh_token token for user: {}", userDetails.getUsername());
        return token;
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsTFunction.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return !isTokenExpired(token) && userDetails.getUsername().equals(username);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
