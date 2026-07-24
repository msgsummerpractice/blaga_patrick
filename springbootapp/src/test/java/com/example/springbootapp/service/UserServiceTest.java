package com.example.springbootapp.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springbootapp.model.User;
import com.example.springbootapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers_ShouldReturnUserList(){

        User randomUser =  new User(1, "John", "Doe", "john.doe@example.com", "password123");
        when(userRepository.findAll()).thenReturn(List.of(randomUser));

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstName());
        verify(userRepository).findAll();
    }

}
