package xyz.msprpayetonkawa.apirevendeur.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.msprpayetonkawa.apirevendeur.qrcode.EmailServiceImpl;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Before
    public void setUp() {
    }

    @Test
    public void testSendSimpleMessage() throws Exception {
        String mail = "text@example.com";
        String objet = "Test Objet";
        String fileToAttach = "chemin/fichier";

        emailService.sendSimpleMessage(mail, objet, fileToAttach);

        ArgumentCaptor<MimeMessagePreparator> argumentCaptor = ArgumentCaptor.forClass(MimeMessagePreparator.class);
        verify(emailSender, times(1)).send(argumentCaptor.capture());

        MimeMessage mimeMessage = new MimeMessage((Session) null);
        MimeMessagePreparator preparator = argumentCaptor.getValue();
        preparator.prepare(mimeMessage);

        assertEquals(mail, mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString());
        assertEquals("epsiprojets@gmail.com", mimeMessage.getFrom()[0].toString());
        assertEquals(objet, mimeMessage.getSubject());

    }

}