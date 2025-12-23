package com.aureumpicks.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:AureumPicks}")
    private String appName;

    public void sendVerificationEmail(String toEmail, String otp) {  // sendVerifcationEmail → sendVerificationEmail
        String subject = "Verify Your Email - " + appName;
        String body = String.format(
                "Dear User,\n\n" +
                        "Thank you for registering with %s!\n\n" +
                        "Your OTP for email verification is: %s\n\n" +  // verifcation → verification
                        "This OTP will expire in 10 minutes.\n\n" +
                        "If you didn't request this, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "%s Team",
                appName, otp, appName
        );
        sendEmail(toEmail, subject, body);
    }

    public void sendPasswordResetEmail(String toEmail, String otp) {
        String subject = "Password Reset Request - " + appName;
        String body = String.format(
                "Dear User,\n\n" +
                        "We received a request to reset your password.\n\n" +
                        "Your OTP for password reset is: %s\n\n" +
                        "This OTP will expire in 10 minutes.\n\n" +
                        "If you didn't request this, please ignore this email and your password will remain unchanged.\n\n" +
                        "Best regards,\n" +
                        "%s Team",
                otp, appName
        );
        sendEmail(toEmail, subject, body);
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
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}