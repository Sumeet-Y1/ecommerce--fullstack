package com.aureumpicks.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:AureumPicks}")
    private String appName;

    @Async
    public void sendVerificationEmail(String toEmail, String otp) {
        sendEmail(toEmail, "Verify Your Email - " + appName,
                String.format("Dear User,\n\nYour OTP is: %s\n\n- %s Team", otp, appName));
    }

    @Async
    public void sendPasswordResetEmail(String toEmail, String otp) {
        sendEmail(toEmail, "Password Reset - " + appName,
                String.format("Dear User,\n\nYour password reset OTP is: %s\n\n- %s Team", otp, appName));
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email asynchronously: " + e.getMessage());
        }
    }
}