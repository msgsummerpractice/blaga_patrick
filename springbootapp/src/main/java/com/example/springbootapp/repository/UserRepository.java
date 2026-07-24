package com.example.springbootapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.springbootapp.model.User;

@Repository 
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User findByFirstName(String firstName) {
        return users.stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
    }

    public void save(User user) {
        users.add(user);
    }
}
