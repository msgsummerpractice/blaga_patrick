package com.example.springbootapp.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springbootapp.model.User;
import com.example.springbootapp.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }


}
