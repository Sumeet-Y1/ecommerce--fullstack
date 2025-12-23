package com.aureumpicks.ecommerce.util;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Component
public class OtpUtil {
    private static final int OTP_LENGTH = 6;  // fnal → final
    private static final int OTP_EXPIRY_MINUTES = 10;  // fnal → final
    private static final SecureRandom random = new SecureRandom();  // fnal → final

    public String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public LocalDateTime getOtpExpiry() {
        return LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
    }

    public boolean isOtpExpired(LocalDateTime otpExpiry) {
        return otpExpiry != null && LocalDateTime.now().isAfter(otpExpiry);
    }

    public boolean verifyOtp(String inputOtp, String storedOtp, LocalDateTime otpExpiry) {
        if (storedOtp == null || inputOtp == null) {
            return false;
        }
        if (isOtpExpired(otpExpiry)) {
            return false;
        }
        return inputOtp.equals(storedOtp);
    }
}