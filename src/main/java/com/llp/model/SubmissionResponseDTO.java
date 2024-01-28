package com.llp.model;

import lombok.Data;

@Data
public class SubmissionResponseDTO {
    private boolean submitted;
    private String restaurantName;

    public SubmissionResponseDTO(boolean submitted, String restaurantName) {
        this.submitted = submitted;
        this.restaurantName = restaurantName;
    }

    // Getters and setters
}

