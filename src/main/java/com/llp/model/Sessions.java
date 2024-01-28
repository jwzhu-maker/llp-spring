package com.llp.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer progress;
    private Integer expectedNumberOfParticipants;

    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Integer ownerId;

    private String status;
    private String result;

    public Sessions(String name, Integer ownerId, int sessionParticipants, String status, int progress) {
        this.name = name;
        this.ownerId = ownerId;
        this.expectedNumberOfParticipants = sessionParticipants;
        this.status = status;
        this.progress = progress;
    }

    public void setWinningRestaurant(String winningRestaurantName) {
        this.result = winningRestaurantName;
    }

    // Getters and setters
}
