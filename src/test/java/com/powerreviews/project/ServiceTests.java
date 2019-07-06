package com.powerreviews.project;

import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.service.ReviewService;
import com.powerreviews.project.service.errors.ForbiddenContentException;
import com.powerreviews.project.service.errors.RestaurantNotFoundException;
import com.powerreviews.project.service.errors.UserRejectedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	private ReviewService reviewService;

	@Test(expected = RestaurantNotFoundException.class)
	public void unknownEateryTest() throws RestaurantNotFoundException {
		reviewService.addReview(19, new RestaurantReviewDto("dee", 3, "root", null));
	}

    @Test(expected = UserRejectedException.class)
    public void rejectedUserTest() {
        reviewService.addReview(1, new RestaurantReviewDto("dee", 3, "Darth Vader", null));
    }

    @Test(expected = ForbiddenContentException.class)
    public void rejectedWordTest() {
        reviewService.addReview(1, new RestaurantReviewDto( "dance n chill ", 3, "Boris", null));
    }

    @Test(expected = ForbiddenContentException.class)
    public void rejectedWord2Test() {
        reviewService.addReview(1, new RestaurantReviewDto("dance n Chill ", 3, "perl", null));
    }
}
