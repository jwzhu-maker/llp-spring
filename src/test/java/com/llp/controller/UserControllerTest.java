package com.llp.controller;

import com.llp.model.LoginRequest;
import com.llp.model.User;
import com.llp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    UserService userService;

    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userController = new UserController(this.userService);
    }

    @Test
    public void loginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUserName");
        User testUser = new User("testUserName");

        List<User> users = new ArrayList<>();
        when(userService.findByUsername(any(String.class))).thenReturn(users);

        ResponseEntity<?> response = userController.login(loginRequest);
        User actualUser = (User) response.getBody();

        assertEquals(ResponseEntity.ok(testUser), response);
        assert actualUser != null;
        assertEquals(testUser.getName(), actualUser.getName());
    }


    @Test
    public void getUserTest() {
        String userName = "testUserName";
        User testUser = new User(userName);

        List<User> users = new ArrayList<>();
        users.add(testUser);
        when(userService.findByUsername(any(String.class))).thenReturn(users);

        ResponseEntity<?> response = userController.getUser(userName);
        User actualUser = (User) response.getBody();

        assertEquals(ResponseEntity.ok(testUser), response);
        assert actualUser != null;
        assertEquals(testUser.getName(), actualUser.getName());
    }
}