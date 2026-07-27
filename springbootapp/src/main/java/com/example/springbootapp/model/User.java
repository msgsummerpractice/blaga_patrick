package com.example.springbootapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    @Email(message = "Email should be valid format")
    private String email;
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    public User(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return this.lastName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }



    
}
