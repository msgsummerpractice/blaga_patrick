package com.example.springdata.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.springdata.dto.SignInResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdata.dto.MfaVerificationRequest;
import com.example.springdata.dto.SignInRequest;
import com.example.springdata.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
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
        boolean isOtpValid = authService.verifyOtp(mfaRequest.getUsername(), mfaRequest.getOtp());
        if (isOtpValid) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(mfaRequest.getUsername());
            String jwtToken = authService.genereateToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

                return ResponseEntity.ok(new SignInResponse(jwtToken, roles));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid OTP. Please try again."));
        }

        
    }
    
    
}
