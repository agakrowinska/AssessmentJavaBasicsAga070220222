package com.example.demo.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{


        //Default configurations
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    @Override

    //Spring will execute it in a separate thread and the caller of the method
    // will not wait till the method is completed execution.
    @Async
    public void send(String to, String email) {
        try{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(email, true);
        helper.setTo(to);
        helper.setSubject("Please confirm your email");
        helper.setFrom("a.h.krowinska@gmail.com");
        mailSender.send(mimeMessage);
    } catch (MessagingException e) {
        LOGGER.error("Failed to send Email", e);
        throw new IllegalStateException("Failed to send Email");
    }
    }
}
