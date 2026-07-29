package com.example.springdata.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdata.dto.MfaVerificationRequest;
import com.example.springdata.dto.SignInRequest;
import com.example.springdata.dto.SignInResponse;
import com.example.springdata.dto.SignUpRequest;
import com.example.springdata.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
                
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        authService.generateAndStoreOtp(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("message", "OTP has been sent to your registered email. Please verify to complete login."));
    }
    
    @PostMapping("/verify-mfa")
    public ResponseEntity<?> verifyMfa(@RequestBody MfaVerificationRequest mfaRequest) {
        try {
            SignInResponse response = authService.verifyMfa(mfaRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            authService.registerUser(signUpRequest);
            return ResponseEntity.ok(Map.of("message", "user registered successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}