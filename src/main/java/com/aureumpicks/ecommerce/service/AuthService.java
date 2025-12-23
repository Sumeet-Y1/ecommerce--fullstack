package com.aureumpicks.ecommerce.service;

import com.aureumpicks.ecommerce.dto.*;
import com.aureumpicks.ecommerce.model.User;
import com.aureumpicks.ecommerce.util.JwtUtil;
import com.aureumpicks.ecommerce.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OtpUtil otpUtil;

    public MessageResponse signup(SignupRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(false);  // setIsVerifed → setIsVerified

        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());

        userService.save(user);

        try {
            emailService.sendVerificationEmail(user.getEmail(), otp);  // sendVerifcationEmail → sendVerificationEmail
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("Signup successful! Please check your email for OTP verification.");  // verifcation → verification
    }

    public MessageResponse verifyEmail(VerifyEmailRequest request) {
        User user = userService.findByEmail(request.getEmail());  // fndByEmail → findByEmail
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (Boolean.TRUE.equals(user.getIsVerified())) {  // getIsVerifed → getIsVerified
            throw new RuntimeException("Email already verified");  // verifed → verified
        }

        if (!otpUtil.verifyOtp(request.getOtp(), user.getOtp(), user.getOtpExpiry())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        user.setIsVerified(true);  // setIsVerifed → setIsVerified
        user.setOtp(null);
        user.setOtpExpiry(null);
        userService.save(user);

        return new MessageResponse("Email verified successfully! You can now login.");  // verifed → verified
    }

    public MessageResponse resendOtp(ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());  // fndByEmail → findByEmail
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (Boolean.TRUE.equals(user.getIsVerified())) {  // getIsVerifed → getIsVerified
            throw new RuntimeException("Email already verified");  // verifed → verified
        }

        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());
        userService.save(user);

        try {
            emailService.sendVerificationEmail(user.getEmail(), otp);  // sendVerifcationEmail → sendVerificationEmail
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("OTP resent successfully! Please check your email.");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());  // fndByEmail → findByEmail
        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!Boolean.TRUE.equals(user.getIsVerified())) {  // getIsVerifed → getIsVerified
            throw new RuntimeException("Please verify your email first");  // frst → first
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail(), "Login successful!");
    }

    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());  // fndByEmail → findByEmail
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());
        userService.save(user);

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), otp);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("Password reset OTP sent to your email.");
    }

    public MessageResponse resetPassword(ResetPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());  // fndByEmail → findByEmail
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!otpUtil.verifyOtp(request.getOtp(), user.getOtp(), user.getOtpExpiry())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userService.save(user);

        return new MessageResponse("Password reset successful! You can now login with your new password.");
    }
}