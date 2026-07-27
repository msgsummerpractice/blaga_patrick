package com.example.springdata.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springdata.model.User;
import com.example.springdata.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail()); 
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                    .map(this::convertToResponse)
                    .toList();
    }

    @Transactional
    public User updateUser(Long id, User newUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (newUser.getUsername() != null) {
            existingUser.setUsername(newUser.getUsername());
            }

        if (newUser.getEmail() != null) {
            existingUser.setEmail(newUser.getEmail());}

        if(newUser.getFirstName() != null) {
            existingUser.setFirstName(newUser.getFirstName());
        }
        if(newUser.getLastName() != null) {
            existingUser.setLastName(newUser.getLastName());
        }
        if(newUser.getPassword() != null) {
            existingUser.setPassword(newUser.getPassword());
        }

        return userRepository.save(existingUser);
    }

    public UserRequest findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
            
        return convertToRequest(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;

                

}
}
