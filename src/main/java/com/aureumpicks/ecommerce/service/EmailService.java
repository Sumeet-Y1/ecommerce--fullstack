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

        String subject = "Verify Your Email - " + appName;
        String htmlContent = getVerificationEmailTemplate(otp);
        sendEmail(toEmail, subject, htmlContent);
    }

    public void sendPasswordResetEmail(String toEmail, String otp) {
        logger.info("=== SENDING PASSWORD RESET EMAIL ===");
        logger.info("To: {}", toEmail);

        String subject = "Password Reset Request - " + appName;
        String htmlContent = getPasswordResetTemplate(otp);
        sendEmail(toEmail, subject, htmlContent);
    }

    // ✨ VERIFICATION EMAIL TEMPLATE
    private String getVerificationEmailTemplate(String otp) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { 
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
                        background-color: #f4f4f4; 
                        margin: 0; 
                        padding: 0; 
                    }
                    .container { 
                        max-width: 600px; 
                        margin: 40px auto; 
                        background: white; 
                        border-radius: 12px; 
                        overflow: hidden;
                        box-shadow: 0 4px 20px rgba(0,0,0,0.1);
                    }
                    .header { 
                        background: linear-gradient(135deg, #C9A961 0%%, #A68B4C 100%%); 
                        color: white; 
                        padding: 40px 20px; 
                        text-align: center; 
                    }
                    .header h1 { 
                        margin: 0; 
                        font-size: 32px; 
                        letter-spacing: 2px;
                        font-weight: 300;
                    }
                    .content { 
                        padding: 40px 30px; 
                    }
                    .otp-box { 
                        background: #FAF8F3; 
                        border: 2px dashed #C9A961; 
                        border-radius: 8px; 
                        padding: 30px; 
                        text-align: center; 
                        margin: 30px 0; 
                    }
                    .otp { 
                        font-size: 36px; 
                        font-weight: bold; 
                        color: #2A2A2A; 
                        letter-spacing: 8px;
                        font-family: 'Courier New', monospace;
                    }
                    .footer { 
                        background: #2A2A2A; 
                        color: #A69B8C; 
                        text-align: center; 
                        padding: 30px 20px; 
                        font-size: 12px; 
                    }
                    .footer a { 
                        color: #C9A961; 
                        text-decoration: none; 
                    }
                    p { 
                        line-height: 1.8; 
                        color: #555; 
                        margin: 15px 0; 
                    }
                    .greeting { 
                        font-size: 18px; 
                        color: #2A2A2A; 
                        font-weight: 500; 
                    }
                    .expiry { 
                        color: #C9A961; 
                        font-weight: 600; 
                        text-align: center; 
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>AUREUM</h1>
                        <p style="margin: 10px 0 0 0; color: rgba(255,255,255,0.9); font-size: 14px;">Curated Luxury</p>
                    </div>
                    <div class="content">
                        <p class="greeting">Welcome to AureumPicks!</p>
                        <p>Thank you for creating an account with us. To complete your registration, please verify your email address using the OTP below:</p>
                        
                        <div class="otp-box">
                            <p style="margin: 0 0 10px 0; color: #A69B8C; font-size: 14px; text-transform: uppercase; letter-spacing: 1px;">Your Verification Code</p>
                            <div class="otp">%s</div>
                        </div>
                        
                        <p class="expiry">⏱️ This code expires in 10 minutes</p>
                        
                        <p>If you didn't create an account with AureumPicks, please ignore this email.</p>
                        
                        <p style="margin-top: 30px; color: #2A2A2A;">Best regards,<br><strong>The AureumPicks Team</strong></p>
                    </div>
                    <div class="footer">
                        <p>© 2025 AureumPicks. All rights reserved.</p>
                        <p>This is an automated email. Please do not reply.</p>
                    </div>
                </div>
            </body>
            </html>
            """, otp);
    }

    // ✨ PASSWORD RESET TEMPLATE
    private String getPasswordResetTemplate(String otp) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { 
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
                        background-color: #f4f4f4; 
                        margin: 0; 
                        padding: 0; 
                    }
                    .container { 
                        max-width: 600px; 
                        margin: 40px auto; 
                        background: white; 
                        border-radius: 12px; 
                        overflow: hidden;
                        box-shadow: 0 4px 20px rgba(0,0,0,0.1);
                    }
                    .header { 
                        background: linear-gradient(135deg, #C9A961 0%%, #A68B4C 100%%); 
                        color: white; 
                        padding: 40px 20px; 
                        text-align: center; 
                    }
                    .header h1 { 
                        margin: 0; 
                        font-size: 32px; 
                        letter-spacing: 2px;
                        font-weight: 300;
                    }
                    .content { 
                        padding: 40px 30px; 
                    }
                    .otp-box { 
                        background: #FFF5E6; 
                        border: 2px solid #C9A961; 
                        border-radius: 8px; 
                        padding: 30px; 
                        text-align: center; 
                        margin: 30px 0; 
                    }
                    .otp { 
                        font-size: 36px; 
                        font-weight: bold; 
                        color: #2A2A2A; 
                        letter-spacing: 8px;
                        font-family: 'Courier New', monospace;
                    }
                    .warning { 
                        background: #FFF3CD; 
                        border-left: 4px solid #FFC107; 
                        padding: 15px; 
                        margin: 20px 0; 
                        border-radius: 4px;
                    }
                    .footer { 
                        background: #2A2A2A; 
                        color: #A69B8C; 
                        text-align: center; 
                        padding: 30px 20px; 
                        font-size: 12px; 
                    }
                    p { 
                        line-height: 1.8; 
                        color: #555; 
                        margin: 15px 0; 
                    }
                    .greeting { 
                        font-size: 18px; 
                        color: #2A2A2A; 
                        font-weight: 500; 
                    }
                    .expiry { 
                        color: #C9A961; 
                        font-weight: 600; 
                        text-align: center; 
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>AUREUM</h1>
                        <p style="margin: 10px 0 0 0; color: rgba(255,255,255,0.9); font-size: 14px;">Curated Luxury</p>
                    </div>
                    <div class="content">
                        <p class="greeting">Password Reset Request</p>
                        <p>We received a request to reset your AureumPicks account password. Use the OTP below to proceed:</p>
                        
                        <div class="otp-box">
                            <p style="margin: 0 0 10px 0; color: #A69B8C; font-size: 14px; text-transform: uppercase; letter-spacing: 1px;">Your Reset Code</p>
                            <div class="otp">%s</div>
                        </div>
                        
                        <p class="expiry">⏱️ This code expires in 10 minutes</p>
                        
                        <div class="warning">
                            <strong>⚠️ Security Notice:</strong><br>
                            If you didn't request a password reset, please ignore this email. Your password will remain unchanged.
                        </div>
                        
                        <p style="margin-top: 30px; color: #2A2A2A;">Best regards,<br><strong>The AureumPicks Team</strong></p>
                    </div>
                    <div class="footer">
                        <p>© 2025 AureumPicks. All rights reserved.</p>
                        <p>This is an automated email. Please do not reply.</p>
                    </div>
                </div>
            </body>
            </html>
            """, otp);
    }

    private void sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            logger.info("Preparing to send email...");

            String json = String.format(
                    "{\"sender\":{\"email\":\"%s\",\"name\":\"%s\"}," +
                            "\"to\":[{\"email\":\"%s\"}]," +
                            "\"subject\":\"%s\"," +
                            "\"htmlContent\":\"%s\"}",
                    fromEmail, appName, toEmail, subject,
                    htmlContent.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")
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
