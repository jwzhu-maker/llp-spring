package com.llp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llp.model.*;
import com.llp.service.RestaurantService;
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

@ContextConfiguration(classes = {RestaurantController.class})
@ExtendWith(SpringExtension.class)
class RestaurantControllerTest {
    @Autowired
    private RestaurantController restaurantController;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserSessionRestaurantService userSessionRestaurantService;

    /**
     * Method under test:
     * {@link RestaurantController#submitRestaurant(RestaurantSubmission)}
     */
    @Test
    void testSubmitRestaurant() throws Exception {
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
        when(userService.findByUsername(Mockito.any())).thenReturn(new ArrayList<>());

        RestaurantSubmission restaurantSubmission = new RestaurantSubmission();
        restaurantSubmission.setRestaurantName("Restaurant Name");
        restaurantSubmission.setSessionId(1);
        restaurantSubmission.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(restaurantSubmission);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submit-restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(restaurantController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test:
     * {@link RestaurantController#submitRestaurant(RestaurantSubmission)}
     */
    @Test
    void testSubmitRestaurant2() throws Exception {
        // Arrange
        when(restaurantService.findByName(Mockito.any())).thenReturn(new ArrayList<>());
        doNothing().when(restaurantService).addRestaurant(Mockito.any());

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

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        doNothing().when(userSessionRestaurantService).addUserSessionRestaurant(Mockito.any());

        RestaurantSubmission restaurantSubmission = new RestaurantSubmission();
        restaurantSubmission.setRestaurantName("Restaurant Name");
        restaurantSubmission.setSessionId(1);
        restaurantSubmission.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(restaurantSubmission);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submit-restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(restaurantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Restaurant submission successful!"));
    }

    /**
     * Method under test:
     * {@link RestaurantController#submitRestaurant(RestaurantSubmission)}
     */
    @Test
    void testSubmitRestaurant3() throws Exception {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setName("?");

        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);
        when(restaurantService.findByName(Mockito.any())).thenReturn(restaurantList);
        doNothing().when(restaurantService).addRestaurant(Mockito.any());

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

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        doNothing().when(userSessionRestaurantService).addUserSessionRestaurant(Mockito.any());

        RestaurantSubmission restaurantSubmission = new RestaurantSubmission();
        restaurantSubmission.setRestaurantName("Restaurant Name");
        restaurantSubmission.setSessionId(1);
        restaurantSubmission.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(restaurantSubmission);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submit-restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(restaurantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Restaurant submission successful!"));
    }

    /**
     * Method under test:
     * {@link RestaurantController#submitRestaurant(RestaurantSubmission)}
     */
    @Test
    void testSubmitRestaurant4() throws Exception {
        // Arrange
        when(restaurantService.findByName(Mockito.any())).thenReturn(new ArrayList<>());
        doNothing().when(restaurantService).addRestaurant(Mockito.any());

        Sessions sessions = new Sessions();
        sessions.setExpectedNumberOfParticipants(0);
        sessions.setId(1);
        sessions.setName("Name");
        sessions.setOwnerId(1);
        sessions.setProgress(1);
        sessions.setResult("Result");
        sessions.setStatus("Status");
        sessions.setWinningRestaurant("Winning Restaurant Name");
        doNothing().when(sessionService).updateSession(Mockito.any());
        when(sessionService.findById(anyInt())).thenReturn(sessions);

        User user = new User();
        user.setId(1);
        user.setName("?");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.findByUsername(Mockito.any())).thenReturn(userList);
        when(userSessionRestaurantService.findBySessionId(anyInt())).thenReturn(new ArrayList<>());
        doNothing().when(userSessionRestaurantService).addUserSessionRestaurant(Mockito.any());

        RestaurantSubmission restaurantSubmission = new RestaurantSubmission();
        restaurantSubmission.setRestaurantName("Restaurant Name");
        restaurantSubmission.setSessionId(1);
        restaurantSubmission.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(restaurantSubmission);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/submit-restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(restaurantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Restaurant submission successful!"));
    }
}
