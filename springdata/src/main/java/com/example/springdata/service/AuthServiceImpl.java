package com.example.springdata.service;

import java.util.Date;
import java.util.HashMap;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;



@Service
public class AuthServiceImpl implements AuthService {


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    @Override
    public String genereateToken(UserDetails userDetails) {
        return Jwts.builder()
                .claims(new HashMap<>())
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis())) 
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public void generateAndStoreOtp(String username) {
        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        otpStorage.put(username, otp);

        System.out.println("Generated OTP for " + username + ": " + otp);
    }

    @Override
    public boolean verifyOtp(String username, String otp) {
        String storedOtp = otpStorage.get(username);
        if(storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(username);
            return true;
        }
        return false;
    }
}
