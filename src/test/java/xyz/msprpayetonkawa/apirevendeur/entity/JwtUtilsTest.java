package xyz.msprpayetonkawa.apirevendeur.entity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.security.jwt.JwtUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Value("${token.secret}")
    private String jwtSecret;

    @Test
    public void testGenerateToken() {
        String username = "testUser";
        String email = "test@example.com";
        String token = jwtUtils.generateToken(username, email);

        assertNotNull(token);
    }

    @Test
    public void testGetUserNameFromJwtToken() {
        String username = "testUser";
        String email = "test@example.com";
        String token = jwtUtils.generateToken(username, email);

        String extractedUsername = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals(email, extractedUsername);
    }

    @Test
    public void testValidateJwtToken() {
        String username = "testUser";
        String email = "test@example.com";
        String token = jwtUtils.generateToken(username, email);

        boolean isValid = jwtUtils.validateJwtToken(token);

        assertTrue(isValid);
    }

    @Test
    public void testValidateJwtTokenInvalid() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    public void testGetJWTFromRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer testToken");

        String token = jwtUtils.getJWTFromRequest(request);

        assertEquals("testToken", token);
    }

    @Test
    public void testGetJWTFromRequestNoBearer() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "testToken");

        String token = jwtUtils.getJWTFromRequest(request);

        assertNull(token);
    }

    @Test
    public void testGetJWTFromRequestNoHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String token = jwtUtils.getJWTFromRequest(request);

        assertNull(token);
    }

    @Test
    public void testValidateJwtTokenInvalidSignature() {
        String tokenWithInvalidSignature = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNjI5MzYwNzI2LCJleHAiOjE2MjkzNjQzMjZ9.invalidsignature";
        boolean isValid = jwtUtils.validateJwtToken(tokenWithInvalidSignature);
        assertFalse(isValid);
    }

    @Test
    public void testValidateJwtTokenMalformed() {
        String malformedToken = "malformedToken";
        boolean isValid = jwtUtils.validateJwtToken(malformedToken);
        assertFalse(isValid);
    }

    @Test
    public void testValidateJwtTokenExpired() {
        Instant now = Instant.now();
        String expiredToken = Jwts.builder()
                .setSubject("testuser@example.com")
                .setExpiration(Date.from(now.minus(5L, ChronoUnit.HOURS)))
                .signWith(jwtUtils.key(), SignatureAlgorithm.HS512)
                .compact();
        assertFalse(jwtUtils.validateJwtToken(expiredToken));
    }

    @Test
    public void testValidateJwtTokenUnsupported() {
        String unsupportedToken = Jwts.builder()
                .setSubject("testuser@example.com")
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256), SignatureAlgorithm.HS256)
                .compact();
        assertFalse(jwtUtils.validateJwtToken(unsupportedToken));
    }

}

