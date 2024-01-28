package com.llp.controller;

import com.llp.model.*;
import com.llp.service.SessionService;
import com.llp.service.UserService;
import com.llp.service.UserSessionRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // Allow requests from other origins (e.g. localhost:3000)
@RestController
@RequestMapping("")
public class SessionController {

    @Autowired
    SessionService sessionService;
    @Autowired
    UserService userService;
    @Autowired
    UserSessionRestaurantService userSessionRestaurantService;

    @PostMapping("/create-session/")
    public ResponseEntity<?> createSession(@RequestBody CreateSessionRequest createSessionRequest) {
        // First, find the user by owner_name to get the owner_id
        List<User> users = userService.findByUsername(createSessionRequest.getOwnerName());
        if (users.isEmpty()) {
            // create a new user
            User newUser = new User(createSessionRequest.getOwnerName());
            userService.addUser(newUser);
            users.add(newUser);
        }
        // Now, create the session with the owner_id
        Sessions session = new Sessions(
                createSessionRequest.getName(),
                users.get(0).getId(),
                createSessionRequest.getSessionParticipants(),
                "open",
                0
        );
        sessionService.createSession(session);
        return ResponseEntity.ok(session);
    }

    @PutMapping("/session/{sessionId}/end")
    public ResponseEntity<?> endSession(@PathVariable int sessionId) {
        // First, find a session
        Sessions session = sessionService.findById(sessionId);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        // Next, update the session status
        session.setStatus("closed");
        sessionService.updateSession(session);

        // Now, randomly select a restaurant from the submissions
        List<UserSessionRestaurant> submissions = userSessionRestaurantService.findBySessionId(sessionId);
        if (submissions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        int randomIndex = (int) (Math.random() * submissions.size());
        UserSessionRestaurant winningSubmission = submissions.get(randomIndex);
        // get the restaurant name from the winning submission
        String winningRestaurantName = winningSubmission.getRestaurant().getName();
        // update the session with the winning restaurant name
        session.setWinningRestaurant(winningRestaurantName);
        sessionService.updateSession(session);

        return ResponseEntity.ok(session);
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getSessions() {
        // Implement get sessions logic here
        List<Sessions> sessions = sessionService.findAll();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/session/{sessionId}/submissions")
    public ResponseEntity<?> getSessionSubmissions(@PathVariable int sessionId) {
        List<UserSessionRestaurant> submissions = userSessionRestaurantService.findBySessionId(sessionId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/session/{sessionId}/owner")
    public ResponseEntity<?> getSessionOwner(@PathVariable int sessionId) {
        Sessions session = sessionService.findById(sessionId);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @GetMapping("/check-submission/{sessionId}/{username}")
    public ResponseEntity<?> checkSubmission(@PathVariable int sessionId, @PathVariable String username) {
        // First, find the user by username
        List<User> users = userService.findByUsername(username);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Next, find the session by session_id
        Sessions session = sessionService.findById(sessionId);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        // Finally, check if the user has submitted a restaurant for the session
        List<UserSessionRestaurant> submissions = userSessionRestaurantService.findBySessionIdAndUserId(sessionId, users.get(0).getId());
        if (submissions.isEmpty()) {
            System.out.println("No submission found");
            return ResponseEntity.ok(new SubmissionResponseDTO(false, ""));
        }
        return ResponseEntity.ok(new SubmissionResponseDTO(true, submissions.get(0).getRestaurant().getName()));
    }

}
