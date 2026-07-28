package com.example.springdata.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    
}
