package com.aureumpicks.ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:AureumPicks}")
    private String appName;

    // Send OTP for email verification
    public void sendVerificationEmail(String toEmail, String otp) {
        String subject = "Verify Your Email - " + appName;
        String body = String.format(
                "Dear User,\n\n" +
                        "Thank you for registering with %s!\n\n" +
                        "Your OTP for email verification is: %s\n\n" +
                        "This OTP will expire in 10 minutes.\n\n" +
                        "If you didn't request this, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "%s Team",
                appName, otp, appName
        );
        sendEmail(toEmail, subject, body);
    }

    // Send OTP for password reset
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

    // Generic email sending with proper MimeMessage (TLS-friendly)
    private void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, false); // false = plain text, true = HTML

            mailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email (MessagingException): " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}