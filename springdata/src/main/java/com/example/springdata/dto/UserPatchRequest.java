package com.example.springdata.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserPatchRequest {
    
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    
}
