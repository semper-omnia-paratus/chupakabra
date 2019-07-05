package com.powerreviews.project.service;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.RestaurantReviewEntity;
import com.powerreviews.project.persistence.RestaurantReviewRepository;
import com.powerreviews.project.service.errors.ForbiddenContentException;
import com.powerreviews.project.service.errors.RestaurantNotFoundException;
import com.powerreviews.project.service.errors.UserRejectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Value("#{'${app.banned.users}'.split(',')}")
    private Set<String> bannedUsers;
    @Value("#{'${app.banned.words}'.split(',')}")
    private Set<String> bannedWords;

    private RestaurantRepository restaurantRepository;

    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    public ReviewServiceImpl(RestaurantRepository restaurantRepository, RestaurantReviewRepository restaurantReviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantReviewRepository = restaurantReviewRepository;
    }

    private boolean containsBannedWords(String text) {
        String cpText = text.toLowerCase();
        for (String word : bannedWords) {
            if (cpText.contains(" " + word.toLowerCase() + " ")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public RestaurantReviewDto addReview(RestaurantReviewDto dto) {
        if (containsBannedWords(dto.getComment())) {
            throw new ForbiddenContentException();
        }
        if (bannedUsers.contains(dto.getUsername())) {
            throw new UserRejectedException();
        }
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

    @Override
    public List<RestaurantReviewDto> getAll() {
        List<RestaurantReviewDto> reviews = new LinkedList<>();
        for (RestaurantReviewEntity restaurantReviewEntity : restaurantReviewRepository.findAll()) {
            reviews.add(restaurantReviewEntity.toDto());
        }
        return reviews;
    }
}
