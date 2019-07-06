package com.powerreviews.project.integration;

import com.powerreviews.project.controller.dto.ErrorInfo;
import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.persistence.RestaurantEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    public static final String MOE_S_PUBLIC_HOUSE = "Moe's Public House";
    public static final String JON_SNOW_3 = "JonSnow3";
    public static final String COMMENT = "Neat place. Thanks";
    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void addAndGetRestaurant() {
        HttpEntity<RestaurantEntity> entity = new HttpEntity<>(new RestaurantEntity(
                MOE_S_PUBLIC_HOUSE,
                "Social house",
                "556",
                "45"));
        ResponseEntity<String> postResponse = restTemplate.exchange(
                baseUri("/restaurants"), HttpMethod.POST, entity, String.class);
        assertEquals(CREATED, postResponse.getStatusCode());

        ResponseEntity<RestaurantEntity> getResponse = restTemplate.exchange(
                baseUri("/restaurants/9"), HttpMethod.GET, entity, RestaurantEntity.class);
        assertNotNull(getResponse.getBody());
        assertEquals(200, getResponse.getStatusCode().value());
        assertEquals(MOE_S_PUBLIC_HOUSE, getResponse.getBody().getName());
    }

    @Test
    public void addRestaurantReviewHappyPath() {
        HttpEntity<RestaurantReviewDto> entity = new HttpEntity<>(new RestaurantReviewDto(
                COMMENT,
                5,
                JON_SNOW_3,
                new Timestamp(new Date().getTime())));
        ResponseEntity<RestaurantReviewDto> postResponse = restTemplate.exchange(
                baseUri("/restaurants/1/reviews"), HttpMethod.POST, entity, RestaurantReviewDto.class);

        RestaurantReviewDto payload = postResponse.getBody();
        assertNotNull(payload);
        assertEquals(CREATED, postResponse.getStatusCode());
        assertTrue(5 == payload.getRating());
        assertEquals(JON_SNOW_3, payload.getUsername());
        assertEquals(COMMENT, payload.getComment());
    }

    @Test
    public void addRestaurantReviewValidationTest() {
        HttpEntity<RestaurantReviewDto> entity = new HttpEntity<>(new RestaurantReviewDto(
                COMMENT,
                6,
                JON_SNOW_3,
                new Timestamp(new Date().getTime())));
        ResponseEntity<ErrorInfo> postResponse = restTemplate.exchange(
                baseUri("/restaurants/1/reviews"), HttpMethod.POST, entity, ErrorInfo.class);

        ErrorInfo payload = postResponse.getBody();
        assertEquals(1, payload.violations.size());
        assertEquals("rating must be between 1 and 5", new ArrayList<>(payload.violations).get(0));

    }

    private String baseUri(String uri) {
        return "http://localhost:" + port + uri;
    }

}
