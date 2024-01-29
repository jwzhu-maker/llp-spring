package com.llp.service;

import com.llp.model.User;
import com.llp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository; // Replace with your actual repository

    public List<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }

    public void addUser(@NonNull User user) {
        userRepository.save(user);
    }

}
