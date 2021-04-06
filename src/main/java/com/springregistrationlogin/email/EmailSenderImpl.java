package com.springregistrationlogin.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void Send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Activate your Account");
            mimeMessageHelper.setFrom("Nabil@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
