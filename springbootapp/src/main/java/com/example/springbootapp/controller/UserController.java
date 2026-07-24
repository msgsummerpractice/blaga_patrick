package com.example.springbootapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootapp.model.User;
import com.example.springbootapp.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getAllUsers(){
        // Added one random user to test in bruno
        userService.registerUser(new User(1, "John", "Doe", "john.doe@example.com", "password123"));
        logger.info("Fetching all users");
        return userService.getAllUsers();
    }
}
