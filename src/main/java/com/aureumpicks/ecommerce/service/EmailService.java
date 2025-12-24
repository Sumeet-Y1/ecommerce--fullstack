package com.aureumpicks.ecommerce.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.name:AureumPicks}")
    private String appName;

    private final OkHttpClient client = new OkHttpClient();

    public void sendVerificationEmail(String toEmail, String otp) {
        logger.info("=== SENDING VERIFICATION EMAIL ===");
        logger.info("To: {}", toEmail);
        logger.info("OTP: {}", otp);
        logger.info("API Key present: {}", brevoApiKey != null && !brevoApiKey.isEmpty());
        logger.info("From Email: {}", fromEmail);

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

    // ... rest of your code

    private void sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            logger.info("Preparing to send email...");

            String json = String.format(
                    "{\"sender\":{\"email\":\"%s\",\"name\":\"%s\"}," +
                            "\"to\":[{\"email\":\"%s\"}]," +
                            "\"subject\":\"%s\"," +
                            "\"htmlContent\":\"%s\"}",
                    fromEmail, appName, toEmail, subject,
                    htmlContent.replace("\"", "\\\"").replace("\n", "")
            );

            logger.info("Request JSON prepared");

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

            logger.info("Making API request to Brevo...");

            try (Response response = client.newCall(request).execute()) {
                logger.info("Response Status: {}", response.code());
                String responseBody = response.body().string();
                logger.info("Response Body: {}", responseBody);

                if (!response.isSuccessful()) {
                    throw new RuntimeException("Failed to send email: " + responseBody);
                }

                logger.info("✅ Email sent successfully!");
            }
        } catch (IOException e) {
            logger.error("❌ Failed to send email", e);
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}