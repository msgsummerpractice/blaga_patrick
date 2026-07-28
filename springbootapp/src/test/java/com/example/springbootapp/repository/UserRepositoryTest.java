package com.example.springbootapp.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.springbootapp.model.User;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    void testFindAll_ShouldReturnUserList() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "password123");
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstName());
    }

    @Test
    void testFindByFirstName_ShouldReturnUser_WhenUserExists() {
        User user = new User(1L, "Jane", "Doe", "jane.doe@example.com", "password123");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByFirstName("Jane");

        assertTrue(foundUser.isPresent());
        assertEquals("Jane", foundUser.get().getFirstName());
    }

    @Test
    void testFindByFirstName_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<User> foundUser = userRepository.findByFirstName("Unknown");

        assertFalse(foundUser.isPresent());
    }
    
}
