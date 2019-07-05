package com.powerreviews.project.service;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;

/**
 * Responsible for Restaurant reviews.
 **/
public interface ReviewService {

    RestaurantReviewDto addReview(RestaurantReviewDto dto) throws RestaurantNotFoundException;

    RestaurantReviewDto retrieveReview(Integer id);


}
