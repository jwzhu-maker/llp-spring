package com.llp.service;

import com.llp.model.UserSessionRestaurant;
import com.llp.repository.UserSessionRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserSessionRestaurantService {

    @Autowired
    UserSessionRestaurantRepository userSessionRestaurantRepository;


    public List<UserSessionRestaurant> findByUserId(int userId) {
        return userSessionRestaurantRepository.findByUserId(userId);
    }

    public List<UserSessionRestaurant> findBySessionId(int sessionId) {
        return userSessionRestaurantRepository.findBySessionId(sessionId);
    }

    public List<UserSessionRestaurant> findByRestaurantId(int restaurantId) {
        return userSessionRestaurantRepository.findByRestaurantId(restaurantId);
    }

    public void addUserSessionRestaurant(@NonNull UserSessionRestaurant userSessionRestaurant) {
        userSessionRestaurantRepository.save(userSessionRestaurant);
    }

    public List<UserSessionRestaurant> findBySessionIdAndUserId(int sessionId, Integer id) {
        return userSessionRestaurantRepository.findBySessionIdAndUserId(sessionId, id);
    }
}

