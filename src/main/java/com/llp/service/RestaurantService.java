package com.llp.service;

import com.llp.model.Restaurant;
import com.llp.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public void addRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findByName(String restaurantName) {
        return restaurantRepository.findByName(restaurantName);
    }

}

