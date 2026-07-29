package com.example.springdata.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MfaVerificationRequest {

    private String username;
    private String otp;
    
}
