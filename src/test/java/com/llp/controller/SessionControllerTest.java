package com.llp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llp.model.*;
import com.llp.service.SessionService;
import com.llp.service.UserService;
import com.llp.service.UserSessionRestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SessionController.class})
@ExtendWith(SpringExtension.class)
class SessionControllerTest {
    @Autowired
    private SessionController sessionController;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserSessionRestaurantService userSessionRestaurantService;

    /**
     * Method under test: {@link SessionController#checkSubmission(int, String)}
     */
    @Test
    void testCheckSubmission() throws Exception {
        // Arrange
        when(userService.findByUsername(Mockito.any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/check-submission/{sessionId}/{username}", 1, "janedoe");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test:
     * {@link SessionController#createSession(CreateSessionRequest)}
     */
    @Test
    void testCreateSession() throws Exception {
        // Arrange
        doNothing().when(sessionService).createSession(Mockito.any());
        when(userService.findByUsername(Mockito.any())).thenReturn(new ArrayList<>());
        doNothing().when(userService).addUser(Mockito.any());

        CreateSessionRequest createSessionRequest = new CreateSessionRequest();
        createSessionRequest.setName("Name");
        createSessionRequest.setOwnerName("Owner Name");
        createSessionRequest.setSessionParticipants(1);
        String content = (new ObjectMapper()).writeValueAsString(createSessionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/create-session/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":\"Name\",\"progress\":0,\"expectedNumberOfParticipants\":1,\"ownerId\":null,\"status\":\"open"
                                        + "\",\"result\":null}"));
    }

    /**
     * Method under test:
     * {@link SessionController#createSession(CreateSessionRequest)}
     */
    @Test
    void testCreateSession2() throws Exception {
        // Arrange
        doNothing().when(sessionService).createSession(Mockito.any());

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);
        doNothing().when(userService).addUser(Mockito.any());

        CreateSessionRequest createSessionRequest = new CreateSessionRequest();
        createSessionRequest.setName("Name");
        createSessionRequest.setOwnerName("Owner Name");
        createSessionRequest.setSessionParticipants(1);
        String content = (new ObjectMapper()).writeValueAsString(createSessionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/create-session/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":\"Name\",\"progress\":0,\"expectedNumberOfParticipants\":1,\"ownerId\":1,\"status\":\"open\","
                                        + "\"result\":null}"));
    }

    /**
     * Method under test: {@link SessionController#checkSubmission(int, String)}
     */
    @Test
    void testCheckSubmission2() throws Exception {
        // Arrange
        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(10);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        when(sessionService.findById(anyInt())).thenReturn(sessions);

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);
        when(userSessionRestaurantService.findBySessionIdAndUserId(anyInt(), Mockito.<Integer>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/check-submission/{sessionId}/{username}", 1, "janedoe");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"submitted\":false,\"restaurantName\":\"\"}"));
    }

    /**
     * Method under test: {@link SessionController#checkSubmission(int, String)}
     */
    @Test
    void testCheckSubmission3() throws Exception {
        // Arrange
        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(10);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        when(sessionService.findById(anyInt())).thenReturn(sessions);

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("?");

        Sessions session = new Sessions();
        session.setExpectedNumberOfParticipants(10);
        session.setId(1);
        session.setName("?");
        session.setOwnerId(1);
        session.setProgress(1);
        session.setResult("?");
        session.setStatus("?");
        session.setWinningRestaurant("?");

        User user2 = new User();
        user2.setId(1);
        user2.setName("?");

        UserSessionRestaurant userSessionRestaurant = new UserSessionRestaurant();
        userSessionRestaurant.setId(1);
        userSessionRestaurant.setRestaurant(restaurant);
        userSessionRestaurant.setSession(session);
        userSessionRestaurant.setUser(user2);

        ArrayList<UserSessionRestaurant> userSessionRestaurantList = new ArrayList<>();
        userSessionRestaurantList.add(userSessionRestaurant);
        when(userSessionRestaurantService.findBySessionIdAndUserId(anyInt(), Mockito.<Integer>any()))
                .thenReturn(userSessionRestaurantList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/check-submission/{sessionId}/{username}", 1, "janedoe");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"submitted\":true,\"restaurantName\":\"?\"}"));
    }

    /**
     * Method under test: {@link SessionController#endSession(int)}
     */
    @Test
    void testEndSession() throws Exception {
        // Arrange
        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(10);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        doNothing().when(sessionService).updateSession(Mockito.any());
        when(sessionService.findById(anyInt())).thenReturn(sessions);
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/session/{sessionId}/end", 1);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link SessionController#endSession(int)}
     */
    @Test
    void testEndSession2() throws Exception {
        // Arrange
        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(10);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        doNothing().when(sessionService).updateSession(Mockito.any());
        when(sessionService.findById(anyInt())).thenReturn(sessions);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("?");

        Sessions session = new Sessions();
        session.setExpectedNumberOfParticipants(10);
        session.setId(1);
        session.setName("?");
        session.setOwnerId(1);
        session.setProgress(1);
        session.setResult("?");
        session.setStatus("?");
        session.setWinningRestaurant("?");

        User user = new User();
        user.setId(1);
        user.setName("?");

        UserSessionRestaurant userSessionRestaurant = new UserSessionRestaurant();
        userSessionRestaurant.setId(1);
        userSessionRestaurant.setRestaurant(restaurant);
        userSessionRestaurant.setSession(session);
        userSessionRestaurant.setUser(user);

        ArrayList<UserSessionRestaurant> userSessionRestaurantList = new ArrayList<>();
        userSessionRestaurantList.add(userSessionRestaurant);
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(userSessionRestaurantList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/session/{sessionId}/end", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"progress\":1,\"expectedNumberOfParticipants\":10,\"ownerId\":1,\"status\":\"closed\","
                                        + "\"result\":\"?\"}"));
    }

    /**
     * Method under test: {@link SessionController#getSessionOwner(int)}
     */
    @Test
    void testGetSessionOwner() throws Exception {
        // Arrange
        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(10);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        when(sessionService.findById(anyInt())).thenReturn(sessions);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/session/{sessionId}/owner", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"progress\":1,\"expectedNumberOfParticipants\":10,\"ownerId\":1,\"status\":\"Status\","
                                        + "\"result\":\"Winning Restaurant Name\"}"));
    }

    /**
     * Method under test: {@link SessionController#getSessionSubmissions(int)}
     */
    @Test
    void testGetSessionSubmissions() throws Exception {
        // Arrange
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/session/{sessionId}/submissions", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link SessionController#getSessionSubmissions(int)}
     */
    @Test
    void testGetSessionSubmissions2() throws Exception {
        // Arrange
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/session/{sessionId}/submissions", 1);
        requestBuilder.characterEncoding("Encoding");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link SessionController#getSessions()}
     */
    @Test
    void testGetSessions() throws Exception {
        // Arrange
        when(sessionService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sessions");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link SessionController#getSessions()}
     */
    @Test
    void testGetSessions2() throws Exception {
        // Arrange
        when(sessionService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sessions");
        requestBuilder.characterEncoding("Encoding");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sessionController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
