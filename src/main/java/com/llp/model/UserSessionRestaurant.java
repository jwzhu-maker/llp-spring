package com.llp.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_session_restaurants")
public class UserSessionRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Sessions session;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    public UserSessionRestaurant(User user, Sessions session, Restaurant restaurant) {
        this.user = user;
        this.session = session;
        this.restaurant = restaurant;
    }

    // Getters and setters
}
