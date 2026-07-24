package com.example.springbootapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootapp.model.User;
import com.example.springbootapp.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getAllUsers(){


        // Added one random user to test in bruno
        userService.registerUser(new User(1, "John", "Doe", "john.doe@example.com", "password123"));
        return userService.getAllUsers();
    }
}
