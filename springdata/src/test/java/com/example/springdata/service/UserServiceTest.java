package com.example.springdata.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springdata.ExceptionHandler.UserNotFoundException;
import com.example.springdata.dto.UserMapper;
import com.example.springdata.dto.UserRequest;
import com.example.springdata.dto.UserResponse;
import com.example.springdata.model.User;
import com.example.springdata.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest("john_doe", "john@example.com", "password123", "John", "Doe");
        User mappedUser = new User(null, "john_doe", "john@example.com", "password123", "John", "Doe");
        User savedUser = new User(1L, "john_doe", "john@example.com", "password123", "John", "Doe");
        UserResponse response = new UserResponse(1L, "john_doe", "john@example.com", "John", "Doe");

        when(userMapper.mapUserRequestToUser(request)).thenReturn(mappedUser);
        when(userRepository.save(mappedUser)).thenReturn(savedUser);
        when(userMapper.mapUserToUserResponse(savedUser)).thenReturn(response);

        UserResponse result = userService.createUser(request);

        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john_doe", result.getUsername());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(userMapper, times(1)).mapUserRequestToUser(request);
        verify(userRepository, times(1)).save(mappedUser);
        verify(userMapper, times(1)).mapUserToUserResponse(savedUser);
    }

    @Test
    void getUserById_whenUserExists_ShouldReturnUserResponse() {
        Long userId = 1L;
        User user = new User(userId, "john_doe", "john@example.com", "pass", "John", "Doe");
        UserResponse response = new UserResponse(userId, "john_doe", "john@example.com", "John", "Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(response);

        Optional<UserResponse> result = userService.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_whenUserDoesNotExist_ShouldReturnEmptyOptional() {
        Long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserResponse> result = userService.findUserById(userId);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).mapUserToUserResponse(any());
    }

    @Test
    void getUserByUsername_whenUserExists_ShouldReturnUserResponse() {
        String username = "john_doe";
        User user = new User(1L, username, "john@example.com", "pass", "John", "Doe");
        UserResponse response = new UserResponse(1L, username, "john@example.com", "John", "Doe");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(response);

        Optional<UserResponse> result = userService.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByEmail_whenUserExists_ShouldReturnUserResponse() {
        String email = "john@example.com";
        User user = new User(1L, "john_doe", email, "pass", "John", "Doe");
        UserResponse response = new UserResponse(1L, "john_doe", email, "John", "Doe");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserResponse(user)).thenReturn(response);

        Optional<UserResponse> result = userService.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUserResponses() {
        User user1 = new User(1L, "john_doe", "john@example.com", "pass", "John", "Doe");
        User user2 = new User(2L, "jane_doe", "jane@example.com", "pass", "Jane", "Doe");
        
        UserResponse response1 = new UserResponse(1L, "john_doe", "john@example.com", "John", "Doe");
        UserResponse response2 = new UserResponse(2L, "jane_doe", "jane@example.com", "Jane", "Doe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.mapUserToUserResponse(user1)).thenReturn(response1);
        when(userMapper.mapUserToUserResponse(user2)).thenReturn(response2);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        assertEquals("jane_doe", result.get(1).getUsername());
        
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).mapUserToUserResponse(any(User.class));
    }

    @Test
    void updateUser_whenUserExists_ShouldReturnUpdatedUserResponse() {
        Long userId = 1L;
        User existingUser = new User(userId, "old_john", "old@example.com", "oldPass", "OldJohn", "OldDoe");
        
        User updateDetails = new User();
        updateDetails.setUsername("new_john");
        updateDetails.setFirstName("NewJohn");

        UserResponse response = new UserResponse(userId, "new_john", "old@example.com", "NewJohn", "OldDoe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        when(userMapper.mapUserToUserResponse(existingUser)).thenReturn(response);

        UserResponse result = userService.updateUser(userId, updateDetails);

        assertNotNull(result);
        assertEquals("new_john", existingUser.getUsername()); 
        assertEquals("NewJohn", existingUser.getFirstName()); 
        assertEquals("old@example.com", existingUser.getEmail());
        
        assertEquals("new_john", result.getUsername());
        
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_whenUserDoesNotExist_ShouldThrowException() {
        Long userId = 99L;
        User updateDetails = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class, 
            () -> userService.updateUser(userId, updateDetails)
        );

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_whenUserExists_ShouldDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_whenUserDoesNotExist_ShouldThrowException() {
        Long userId = 99L;

        when(userRepository.existsById(userId)).thenReturn(false);

        UserNotFoundException exception = assertThrows(
            UserNotFoundException.class, 
            () -> userService.deleteUser(userId)
        );

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}