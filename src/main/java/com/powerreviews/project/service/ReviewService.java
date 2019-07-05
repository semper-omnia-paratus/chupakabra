package com.powerreviews.project.service;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.service.errors.RestaurantNotFoundException;

import java.util.List;

/**
 * Responsible for Restaurant reviews.
 **/
public interface ReviewService {

    RestaurantReviewDto addReview(RestaurantReviewDto dto);

    RestaurantReviewDto retrieveReview(Integer id);

    List<RestaurantReviewDto> getAll();
}
