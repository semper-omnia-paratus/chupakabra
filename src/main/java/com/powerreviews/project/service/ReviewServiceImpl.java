package com.powerreviews.project.service;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.RestaurantReviewEntity;
import com.powerreviews.project.persistence.RestaurantReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private RestaurantRepository restaurantRepository;

    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    public ReviewServiceImpl(RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
    }

    @Override
    public RestaurantReviewDto addReview(RestaurantReviewDto dto) throws RestaurantNotFoundException {
        RestaurantEntity restaurant = restaurantRepository.findByName(dto.getRestaurantName());
        if (dto.getRestaurantName() == null || restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        RestaurantReviewEntity restaurantReviewEntity = new RestaurantReviewEntity();
        restaurantReviewEntity.setRestaurant(restaurant);
        restaurantReviewEntity.setComment(dto.getComment());
        restaurantReviewEntity.setRating(dto.getRating());
        restaurantReviewEntity.setUsername(dto.getUsername());
        return restaurantReviewRepository.save(restaurantReviewEntity).toDto();
    }

    @Override
    public RestaurantReviewDto retrieveReview(Integer id) {
        return restaurantReviewRepository.findById(id).get().toDto();
    }
}
