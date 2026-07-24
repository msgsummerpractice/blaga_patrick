package com.example.springdata.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springdata.model.User;
import com.example.springdata.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        User newUser =  new User();
        newUser.setUsername("testuser");
        newUser.setEmail("testuser@example.com");
        newUser.setPassword("password");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("testuser@example.com");
        savedUser.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(newUser);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_whenUserExists_ShouldReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        User result = userService.findUserById(userId);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserByUsername_whenUserExists_ShouldReturnUser() {
        String username = "testuser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        User result = userService.findUserByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsername_whenUserDoesNotExist_ShouldThrowException() {
        String username = "nonexistentuser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        try {
            userService.findUserByUsername(username);
        } catch (RuntimeException e) {
            assertEquals("User not found with username: " + username, e.getMessage());
        }

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByEmail_whenUserExists_ShouldReturnUser(){
        String email = "testuser@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        User result = userService.findUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getUserByEmail_whenUserDoesNotExist_ShouldThrowException() {
        String email = "testuser@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        try {
            userService.findUserByEmail(email);
        } catch (RuntimeException e) {
            assertEquals("User not found with email: " + email, e.getMessage());
        }
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getUserById_whenUserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        try {
            userService.findUserById(userId);
        } catch (RuntimeException e) {
            assertEquals("User not found with id: " + userId, e.getMessage());
        }

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(java.util.Arrays.asList(user1, user2));

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser_whenUserExists_ShouldDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

}
