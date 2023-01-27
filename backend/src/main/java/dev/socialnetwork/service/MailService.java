package dev.socialnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final String defaultFrontendUrl;

    private final String senderMail;

    @Autowired
    public MailService(JavaMailSender javaMailSender,
                       @Value("${application.frontend.default-url}") String defaultFrontendUrl,
                       @Value("${spring.mail.username}") String senderMail) {
        this.javaMailSender = javaMailSender;
        this.defaultFrontendUrl = defaultFrontendUrl;
        this.senderMail = senderMail;
    }

    public void sendForgotMessage(String email, String token, String baseUrl){
        var url = baseUrl !=null ? baseUrl : defaultFrontendUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderMail);
        message.setTo(email);
        message.setSubject("Reset your password");
        message.setText(String.format("Click %s/reset/%s to reset your password.", url, token));
        javaMailSender.send(message);
    }
}
