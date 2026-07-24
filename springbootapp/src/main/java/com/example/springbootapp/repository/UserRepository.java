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

    public void save(User user) {
        users.add(user);
    }
}
