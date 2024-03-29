package com.llp.service;

import com.llp.model.Restaurant;
import com.llp.repository.RestaurantRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void addRestaurant(@NonNull Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findByName(String restaurantName) {
        return restaurantRepository.findByName(restaurantName);
    }

}

