package com.llp.service;

import com.llp.model.UserSessionRestaurant;
import com.llp.repository.UserSessionRestaurantRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSessionRestaurantService {

    UserSessionRestaurantRepository userSessionRestaurantRepository;

    public UserSessionRestaurantService(UserSessionRestaurantRepository userSessionRestaurantRepository) {
        this.userSessionRestaurantRepository = userSessionRestaurantRepository;
    }

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

