package com.example.springdata.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    String genereateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    void generateAndStoreOtp(String username);
    boolean verifyOtp(String username, String otp);
    
}
