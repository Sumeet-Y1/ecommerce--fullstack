package com.aureumpicks.ecommerce.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:AureumPicks}")
    private String appName;

    private final OkHttpClient client = new OkHttpClient();

    public void sendVerificationEmail(String toEmail, String otp) {
        String subject = "Verify Your Email - " + appName;
        String htmlContent = String.format(
                "<html><body>" +
                        "<h2>Dear User,</h2>" +
                        "<p>Thank you for registering with %s!</p>" +
                        "<h3>Your OTP for email verification is: <strong>%s</strong></h3>" +
                        "<p>This OTP will expire in 10 minutes.</p>" +
                        "<p>If you didn't request this, please ignore this email.</p>" +
                        "<p>Best regards,<br>%s Team</p>" +
                        "</body></html>",
                appName, otp, appName
        );
        sendEmail(toEmail, subject, htmlContent);
    }

    public void sendPasswordResetEmail(String toEmail, String otp) {
        String subject = "Password Reset Request - " + appName;
        String htmlContent = String.format(
                "<html><body>" +
                        "<h2>Dear User,</h2>" +
                        "<p>We received a request to reset your password.</p>" +
                        "<h3>Your OTP for password reset is: <strong>%s</strong></h3>" +
                        "<p>This OTP will expire in 10 minutes.</p>" +
                        "<p>If you didn't request this, please ignore this email and your password will remain unchanged.</p>" +
                        "<p>Best regards,<br>%s Team</p>" +
                        "</body></html>",
                otp, appName
        );
        sendEmail(toEmail, subject, htmlContent);
    }

    private void sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            String json = String.format(
                    "{\"sender\":{\"email\":\"%s\",\"name\":\"%s\"}," +
                            "\"to\":[{\"email\":\"%s\"}]," +
                            "\"subject\":\"%s\"," +
                            "\"htmlContent\":\"%s\"}",
                    fromEmail, appName, toEmail, subject,
                    htmlContent.replace("\"", "\\\"").replace("\n", "")
            );

            RequestBody body = RequestBody.create(
                    json,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url("https://api.brevo.com/v3/smtp/email")
                    .addHeader("api-key", brevoApiKey)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("accept", "application/json")
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Failed to send email: " + response.body().string());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}