package xyz.msprpayetonkawa.apirevendeur.entity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.security.jwt.JwtUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class JwtUtilsTest {
    private final JwtUtils jwtUtils = new JwtUtils();

    @Value("${token.secret}")
    private String jwtSecret;

    @Mock
    private Key key;


    @Test
    public void testGetJWTFromRequestWithGoodToken(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer token");

        String jwt = jwtUtils.getJWTFromRequest(request);
        assertEquals("token", jwt);
    }

    @Test
    public void testGetJWTFromRequestWithNoToken(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn(null);

        String jwt = jwtUtils.getJWTFromRequest(request);
        assertNull(jwt);
    }

    @Test
    public void testGetJWTFromRequestWithWrongToken(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getHeader("Authorization")).thenReturn("wrong token");

        String jwt = jwtUtils.getJWTFromRequest(request);
        assertNull(jwt);
    }

    @Test
    public void testGenerateToken(){
        String username = "testUser";
        String email = "test@example.com";

        Instant now = Instant.now();
        String generatedToken = jwtUtils.generateToken(username, email);

        Jws<Claims> parsedToken = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getEncoded())).build()
                .parseClaimsJws(generatedToken);

        assertEquals(username, parsedToken.getBody().get("name", String.class));
        assertEquals("ROLE_RETAILER", parsedToken.getBody().get("role", String.class));
        assertEquals(email, parsedToken.getBody().getSubject());
        assertTrue(Date.from(now).before(parsedToken.getBody().getIssuedAt()));
        assertTrue(Date.from(now.plus(5L, ChronoUnit.HOURS)).after(parsedToken.getBody().getExpiration()));

    }


}

