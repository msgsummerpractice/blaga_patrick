package com.example.springbootapp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootapp.config.AppSettings;
import com.example.springbootapp.model.User;
import com.example.springbootapp.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;


@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;
    private final AppSettings appSettings;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${app.user.welcome.message}")
    private String welcomeMessage;

    @Autowired
    public UserController(UserService userService, AppSettings appSettings) {
        this.userService = userService;
        this.appSettings = appSettings;
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) @Size(min = 3, message = "Minimum size is 3")String role) {
        // Added one random user to test in bruno
        userService.registerUser(new User(1L, "John", "Doe", "john.doe@example.com", "password123"));
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMessage() {
        logger.info("Fetching welcome message");
        return ResponseEntity.ok(welcomeMessage);
    }

    @GetMapping("/settings")
    public ResponseEntity<String> getAppSettings() {
        logger.info("Fetching application settings");
        return ResponseEntity.ok(" Max Users: " + appSettings.getMaxUsers() + ", Theme: " + appSettings.getTheme());
    }

    @GetMapping("/{firstName}")
    public ResponseEntity<User> getUserByFirstName(
        @PathVariable @Size(min = 3, message = "Minimum size is 3") String firstName) {

        logger.info("Fetching user by first name: {}", firstName);

        Optional<User> user = userService.getUserByFirstName(firstName);

        if (user.isPresent()) {
         return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Optional<User>> registerUser(@Valid @RequestBody User user) {
        logger.info("Registering user: {}", user.getEmail());
        userService.registerUser(user);
        User savedUser = userService.getUserByFirstName(user.getFirstName()).orElse(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(savedUser));
    }
    

}


