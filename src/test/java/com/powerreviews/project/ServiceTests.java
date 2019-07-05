package com.powerreviews.project;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.RestaurantReviewEntity;
import com.powerreviews.project.persistence.RestaurantReviewRepository;
import com.powerreviews.project.service.RestaurantNotFoundException;
import com.powerreviews.project.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	private ReviewService reviewService;


	@Test(expected = RestaurantNotFoundException.class)
	public void unknownEateryTest() throws RestaurantNotFoundException {
		reviewService.addReview(new RestaurantReviewDto("Bar", "dee", 3, "root", null));
	}

}
