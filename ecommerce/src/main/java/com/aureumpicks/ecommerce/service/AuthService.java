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

    // User Signup
    public MessageResponse signup(SignupRequest request) {
        // Check if user already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(false);

        // Generate and set OTP
        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());

        // Save user
        userService.save(user);

        // Send verification email
        try {
            emailService.sendVerificationEmail(user.getEmail(), otp);
        } catch (Exception e) {
            // If email fails, still return success but log the error
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("Signup successful! Please check your email for OTP verification.");
    }

    // Verify Email
    public MessageResponse verifyEmail(VerifyEmailRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (Boolean.TRUE.equals(user.getIsVerified())) {
            throw new RuntimeException("Email already verified");
        }

        // Verify OTP
        if (!otpUtil.verifyOtp(request.getOtp(), user.getOtp(), user.getOtpExpiry())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Mark user as verified
        user.setIsVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userService.save(user);

        return new MessageResponse("Email verified successfully! You can now login.");
    }

    // Resend OTP
    public MessageResponse resendOtp(ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (Boolean.TRUE.equals(user.getIsVerified())) {
            throw new RuntimeException("Email already verified");
        }

        // Generate new OTP
        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());
        userService.save(user);

        // Send email
        try {
            emailService.sendVerificationEmail(user.getEmail(), otp);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("OTP resent successfully! Please check your email.");
    }

    // Login
    public AuthResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!Boolean.TRUE.equals(user.getIsVerified())) {
            throw new RuntimeException("Please verify your email first");
        }

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail(), "Login successful!");
    }

    // Forgot Password
    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Generate OTP
        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(otpUtil.getOtpExpiry());
        userService.save(user);

        // Send password reset email
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), otp);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return new MessageResponse("Password reset OTP sent to your email.");
    }

    // Reset Password
    public MessageResponse resetPassword(ResetPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Verify OTP
        if (!otpUtil.verifyOtp(request.getOtp(), user.getOtp(), user.getOtpExpiry())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userService.save(user);

        return new MessageResponse("Password reset successful! You can now login with your new password.");
    }
}