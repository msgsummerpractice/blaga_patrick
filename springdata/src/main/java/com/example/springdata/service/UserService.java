package com.example.springdata.service;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springdata.ExceptionHandler.UserNotFoundException;
import com.example.springdata.dto.UserPatchRequest;
import com.example.springdata.dto.UserRequest;
import com.example.springdata.dto.UserResponse;
import com.example.springdata.mapper.UserMapper;
import com.example.springdata.model.User;
import com.example.springdata.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = userMapper.mapUserRequestToUser(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::mapUserToUserResponse);
    }

    @Transactional
    public UserResponse updateUser(Long id, User newUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

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

        return userMapper.mapUserToUserResponse(userRepository.save(existingUser));
    }

    public Optional<UserResponse> findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapUserToUserResponse);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public Optional<UserResponse> findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapUserToUserResponse);
    }

    public Optional<UserResponse> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapUserToUserResponse);
    }

    public Optional<UserResponse> partialUpdate(Long id, UserPatchRequest request) {

    return userRepository.findById(id)
            .map(user -> {

                if (request.getUsername() != null) {
                    user.setUsername(request.getUsername());
                }

                if (request.getEmail() != null) {
                    user.setEmail(request.getEmail());
                }

                if (request.getPassword() != null) {
                    user.setPassword(request.getPassword());
                }

                return userMapper.mapUserToUserResponse(userRepository.save(user));
            });
}

}
