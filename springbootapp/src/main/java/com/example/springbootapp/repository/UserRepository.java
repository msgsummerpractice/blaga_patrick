package com.example.springbootapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.springbootapp.model.User;

@Repository 
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }


    public Optional<User> findByFirstName(String firstName) {
        return users.stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                .findFirst();
    }

    public void save(User user) {
        users.add(user);
    }
}
