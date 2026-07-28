package com.example.springdata.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.springdata.dto.SignInResponse;
import com.example.springdata.dto.SignUpRequest;
import com.example.springdata.model.Role;
import com.example.springdata.model.User;
import com.example.springdata.dto.SignInResponse;
import com.example.springdata.repository.UserRepository;
import com.example.springdata.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                          UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "username is taken"));
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("no role found"));
        user.getRoles().add(userRole);

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "user registered successfully"));
    }
    
    
    
}
