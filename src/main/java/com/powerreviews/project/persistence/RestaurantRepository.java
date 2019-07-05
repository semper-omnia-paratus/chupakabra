package com.powerreviews.project.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
    @Query("SELECT MAX(id) FROM restaurant")
    int maxId();

    RestaurantEntity findByName(String name);

    @Query(value = "SELECT r FROM restaurant r LEFT JOIN restaurant_review review ON review.restaurant = r\n" +
                    "GROUP BY r\n" +
                    "ORDER BY AVG(review.rating) DESC")
    List<RestaurantEntity> sortedFromHighestToLowestAverageRating();
}
