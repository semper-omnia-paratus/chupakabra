package com.powerreviews.project;

import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import com.powerreviews.project.persistence.RestaurantReviewEntity;
import com.powerreviews.project.persistence.RestaurantReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTests {
	public static final String CHIPOTLE = "Chipotle";
	public static final String ALOHA = "Aloha";

	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private RestaurantReviewRepository restaurantReviewRepository;

	@Test
	public void sortedByAvgRating() {
		RestaurantEntity r = restaurantRepository.findByName(CHIPOTLE);
		RestaurantReviewEntity review = new RestaurantReviewEntity();
		review.setRating(2);
		review.setRestaurant(r);
		review.setUsername("Jon");
		review.setComment("Belissimo!");
		restaurantReviewRepository.save(review);
		List<RestaurantEntity> collection = restaurantRepository.sortedFromHighestToLowestAverageRating();
		assertEquals("Sorted aggregation did not work out", CHIPOTLE, collection.get(0).getName());
		RestaurantReviewEntity review2 = new RestaurantReviewEntity();
		review2.setRating(5);
		review2.setRestaurant(restaurantRepository.findByName(ALOHA));
		review2.setUsername("Lilly Allen");
		review2.setComment("Great deals on Wed! Hawaiian vibe");
		restaurantReviewRepository.save(review2);
		List<RestaurantEntity> collection2 = restaurantRepository.sortedFromHighestToLowestAverageRating();
		assertEquals("Sorted aggregation did not work out", ALOHA, collection2.get(0).getName());
		assertEquals("Sorted aggregation did not work out", CHIPOTLE, collection2.get(1).getName());
	}

}
