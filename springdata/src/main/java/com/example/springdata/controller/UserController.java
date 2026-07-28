package com.example.springdata.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springdata.dto.UserPatchRequest;
import com.example.springdata.dto.UserRequest;
import com.example.springdata.dto.UserResponse;
import com.example.springdata.model.User;
import com.example.springdata.repository.UserRepository;
import com.example.springdata.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value="/api/users", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@PageableDefault(page = 0 ,size = 10) Pageable pageable) {
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {
        UserResponse createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        return userService.findUserById(id)
                .map(existingUser -> {
                    User updatedUser = new User();
                    updatedUser.setUsername(userRequest.getUsername());
                    updatedUser.setEmail(userRequest.getEmail());
                    updatedUser.setPassword(userRequest.getPassword());
                    updatedUser.setFirstName(userRequest.getFirstName());
                    updatedUser.setLastName(userRequest.getLastName());
                    UserResponse updatedUserResponse = userService.updateUser(id, updatedUser);
                    return ResponseEntity.ok(updatedUserResponse);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> partialUpdateUser(@PathVariable Long id, @RequestBody UserPatchRequest userPatchRequest) {
           return userService.findUserById(id)
                .map(existingUser -> {
                    User updatedUser = new User();
                    if (userPatchRequest.getUsername() != null) {
                        updatedUser.setUsername(userPatchRequest.getUsername());
                    }
                    if (userPatchRequest.getEmail() != null) {
                        updatedUser.setEmail(userPatchRequest.getEmail());
                    }
                    if (userPatchRequest.getPassword() != null) {
                        updatedUser.setPassword(userPatchRequest.getPassword());
                    }
                    if (userPatchRequest.getFirstName() != null) {
                        updatedUser.setFirstName(userPatchRequest.getFirstName());
                    }
                    if (userPatchRequest.getLastName() != null) {
                        updatedUser.setLastName(userPatchRequest.getLastName());
                    }
                    UserResponse updatedUserResponse = userService.updateUser(id, updatedUser);
                    return ResponseEntity.ok(updatedUserResponse);
                })
                .orElse(ResponseEntity.notFound().build());
        }
    
    
    
    
}
