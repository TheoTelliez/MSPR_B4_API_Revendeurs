package xyz.msprpayetonkawa.apirevendeur.qrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String fileToAttach) throws MessagingException {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress("epsiprojets@gmail.com"));
                mimeMessage.setSubject(subject);

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText("<html>\n" +
                        "<head></head>\n" +
                        "<body>\n" +
                        "<p>Bonjour, voici votre QR Code de connexion à l'application PayeTonKawa:</p>\n" +
                        "<img width=\"500\" height=\"500\" src=\"cid:qrcode\">\n" +
                        "</body>\n" +
                        "</html>", true);

                FileSystemResource res = new FileSystemResource(new File(fileToAttach));
                helper.addInline("qrcode", res);
            }
        };

        emailSender.send(preparator);
    }


}