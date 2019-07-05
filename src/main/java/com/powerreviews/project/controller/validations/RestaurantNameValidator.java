package com.powerreviews.project.controller.validations;

import com.powerreviews.project.persistence.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RestaurantNameValidator implements ConstraintValidator<RestaurantNameConstraint, String> {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantNameValidator(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void initialize(RestaurantNameConstraint contactNumber) {
    }
 
    @Override
    public boolean isValid(String restaurantName, ConstraintValidatorContext cxt) {
        return restaurantName != null && restaurantRepository.findByName(restaurantName) != null;
    }
 
}