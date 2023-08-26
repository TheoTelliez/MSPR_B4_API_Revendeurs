package xyz.msprpayetonkawa.apirevendeur.entity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.security.jwt.JwtUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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

    @Test
    public void testValidateJwtTokenEmptyClaims() {
        String tokenWithEmptyClaims = Jwts.builder()
                .setHeaderParam("alg", "HS512")
                .setClaims(new HashMap<>())  // set empty claims
                .signWith(jwtUtils.key())
                .compact();
        assertFalse(jwtUtils.validateJwtToken(tokenWithEmptyClaims));
    }

}