package com.powerreviews.project.controller.dto;

import com.powerreviews.project.controller.validations.RestaurantNameConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class RestaurantReviewDto {
    private Integer id;
    private String comment;
    private Integer rating;
    private String username;
    private Timestamp timestamp;

    public RestaurantReviewDto(String comment, Integer rating, String username, Timestamp timestamp) {
        this.comment = comment;
        this.rating = rating;
        this.username = username;
        this.timestamp = timestamp;
    }
}
