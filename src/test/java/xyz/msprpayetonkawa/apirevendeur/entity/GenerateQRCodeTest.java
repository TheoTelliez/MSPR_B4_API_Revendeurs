package xyz.msprpayetonkawa.apirevendeur.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.WebSecurityConfig;
import xyz.msprpayetonkawa.apirevendeur.qrcode.EmailServiceImpl;
import xyz.msprpayetonkawa.apirevendeur.qrcode.GenerateQRCode;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(WebSecurityConfig.class)
public class GenerateQRCodeTest {

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private GenerateQRCode generateQRCode;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateQRCode() throws Exception {
        String token = "testToken";
        String username = "testUsername";
        String email = "test@example.com";

        generateQRCode.createQRCode(token, username, email);

        Mockito.verify(emailService).sendSimpleMessage(eq(email), eq("QRCode"), anyString());
    }

    @Test
    public void testQRCodeCreation() throws Exception {
        String token = "testToken";
        String username = "testUsername";
        String email = "test@example.com";

        generateQRCode.createQRCode(token, username, email);

        assertTrue(new File("/qrcodes/testUsername.png").exists());
        Files.delete(Path.of("/qrcodes/" + username + ".png"));
    }

    @Test
    public void testQRCodeCreationNoFile() throws Exception {
        String token = "testToken";
        String username = UUID.randomUUID().toString();
        String email = "test@example.com";

        generateQRCode.createQRCode(token, username, email);

        assertTrue(new File("/qrcodes/"+username+".png").exists());
        Files.delete(Path.of("/qrcodes/" + username + ".png"));
    }


}
