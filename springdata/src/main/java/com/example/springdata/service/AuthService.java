package com.example.springdata.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.springdata.dto.MfaVerificationRequest;
import com.example.springdata.dto.SignInResponse;
import com.example.springdata.dto.SignUpRequest;

public interface AuthService {

    String genereateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    void generateAndStoreOtp(String username);
    boolean verifyOtp(String username, String otp);
    void registerUser(SignUpRequest signUpRequest);
    SignInResponse verifyMfa(MfaVerificationRequest mfaRequest);
    
}
