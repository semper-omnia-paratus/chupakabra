package com.powerreviews.project.service;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;

import java.util.List;

/**
 * Responsible for Restaurant reviews.
 **/
public interface ReviewService {

    RestaurantReviewDto addReview(Integer id, RestaurantReviewDto dto);

    RestaurantReviewDto retrieveReview(Integer id);

    List<RestaurantReviewDto> getAll();
}
