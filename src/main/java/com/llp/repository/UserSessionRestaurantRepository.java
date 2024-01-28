package com.llp.repository;

import com.llp.model.UserSessionRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSessionRestaurantRepository extends JpaRepository<UserSessionRestaurant, Integer> {
    List<UserSessionRestaurant> findByUserId(int userId);

    List<UserSessionRestaurant> findBySessionId(int sessionId);

    List<UserSessionRestaurant> findByRestaurantId(int restaurantId);

    List<UserSessionRestaurant> findBySessionIdAndUserId(int sessionId, Integer userId);
}
