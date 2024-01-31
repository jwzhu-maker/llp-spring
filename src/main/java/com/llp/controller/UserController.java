package com.llp.controller;

import com.llp.model.LoginRequest;
import com.llp.model.User;
import com.llp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // Allow requests from other origins (e.g. localhost:3000)
@RestController
@RequestMapping("")
public class UserController {

    UserService userService; // Make sure to create UserService

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login/")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        try {
            List<User> users = userService.findByUsername(loginRequest.getUsername());
            User user;
            if (users.isEmpty()) {
                // create a new user
                user = new User(loginRequest.getUsername());
                userService.addUser(user);
            } else {
                user = users.get(0);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        try {
            List<User> users = userService.findByUsername(userName);
            if (users.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(users.get(0));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
