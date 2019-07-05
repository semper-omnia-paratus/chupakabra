package com.powerreviews.project.controller;

import com.powerreviews.project.persistence.RestaurantEntity;
import com.powerreviews.project.persistence.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(@Autowired RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> post(@RequestBody RestaurantEntity restaurant) {
        return new ResponseEntity<>(restaurantRepository.save(restaurant), new HttpHeaders(), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> put(@RequestBody RestaurantEntity restaurant) {
        RestaurantEntity updated = restaurantRepository.findById(restaurant.getId()).orElse(null);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        updated.setLatitude(restaurant.getLatitude());
        updated.setLongitude(restaurant.getLongitude());
        updated.setName(restaurant.getName());
        updated.setType(restaurant.getType());
        restaurantRepository.save(updated);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> delete(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantEntity> get(@PathVariable Integer id) {
        RestaurantEntity restaurant = restaurantRepository.findById(id).orElse(null);
        return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantEntity>> getCollection(@RequestParam(value = "query", required = false) String query) {
        if (query.equals("sortedFromHighestToLowestAverageRating")) {
            List<RestaurantEntity> restaurant = restaurantRepository.sortedFromHighestToLowestAverageRating();
            return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } else {
            return new ResponseEntity<>(restaurantRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
        }
    }


}
