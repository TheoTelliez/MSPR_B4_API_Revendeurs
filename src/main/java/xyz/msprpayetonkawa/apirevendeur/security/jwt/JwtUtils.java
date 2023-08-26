package xyz.msprpayetonkawa.apirevendeur.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {
    private static final Logger loggerMessage = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${token.secret}")
    private String jwtSecret;

    public String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Key key() {
        StringBuilder reverse = new StringBuilder(jwtSecret).reverse();
        String jwtKey = jwtSecret + reverse + jwtSecret + reverse;
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKey));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            loggerMessage.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            loggerMessage.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            loggerMessage.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            loggerMessage.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateToken(String username, String email) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("name", username)
                .claim("role", "ROLE_RETAILER")
                .setSubject(email)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.HOURS)))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }
}
