package com.llp.controller;

import com.llp.model.*;
import com.llp.service.RestaurantService;
import com.llp.service.SessionService;
import com.llp.service.UserService;
import com.llp.service.UserSessionRestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // Allow requests from other origins (e.g. localhost:3000)
@RestController
@RequestMapping("")
public class RestaurantController {

    RestaurantService restaurantService;
    SessionService sessionService;
    UserService userService;
    UserSessionRestaurantService userSessionRestaurantService;

    public RestaurantController(RestaurantService restaurantService, SessionService sessionService, UserService userService, UserSessionRestaurantService userSessionRestaurantService) {
        this.restaurantService = restaurantService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.userSessionRestaurantService = userSessionRestaurantService;
    }

    @PostMapping("/submit-restaurant/")
    public ResponseEntity<String> submitRestaurant(@RequestBody RestaurantSubmission submission) {
        // First, validate the session ID
        Sessions session = sessionService.findById(submission.getSessionId());
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        // Next, get the user ID from the username
        List<User> user = userService.findByUsername(submission.getUserName());
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Next, check if the restaurant already exists, and if not, create it
        List<Restaurant> restaurant = restaurantService.findByName(submission.getRestaurantName());
        if (restaurant.isEmpty()) {
            Restaurant newRestaurant = new Restaurant();
            newRestaurant.setName(submission.getRestaurantName());
            restaurantService.addRestaurant(newRestaurant);
            restaurant.add(newRestaurant);
        }
        // Add the restaurant submission to the database
        userSessionRestaurantService.addUserSessionRestaurant
                (new UserSessionRestaurant(user.get(0), session, restaurant.get(0)));


        // Update session progress
        // 1. Select the total number of submissions for the session
        List<UserSessionRestaurant> submissions = userSessionRestaurantService.findBySessionId(submission.getSessionId());

        // 2. Update the session progress
        Integer participants = session.getExpectedNumberOfParticipants();
        if (participants > 0) {
            session.setProgress(submissions.size() * 100 / participants);
        } else {
            session.setProgress(0);
        }
        sessionService.updateSession(session);

        String message = "Restaurant submission successful!";
        return ResponseEntity.ok().body(message);
    }

}
