package com.powerreviews.project.controller;

import com.powerreviews.project.controller.dto.ErrorInfo;
import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.service.ReviewService;
import com.powerreviews.project.service.errors.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantReviewController {
    private final ReviewService reviewService;

    @Autowired
    public RestaurantReviewController(ReviewService rs) {
        this.reviewService = rs;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RestaurantReviewDto>> getAll() {
        List<RestaurantReviewDto> reviews = reviewService.getAll();
        return new ResponseEntity<>(reviews, new HttpHeaders(), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestaurantReviewDto> get(@NotNull @PathVariable Integer id) {
        RestaurantReviewDto review = reviewService.retrieveReview(id);
        return new ResponseEntity<>(review, new HttpHeaders(), review == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestaurantReviewDto> post(@Valid @RequestBody RestaurantReviewDto review) throws RestaurantNotFoundException {
        return new ResponseEntity<>(reviewService.addReview(review), new HttpHeaders(), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> handleConstraintViolation(ConstraintViolationException ex, ServletWebRequest req) {

        return new ResponseEntity<>(
                new ErrorInfo(req.getRequest().getRequestURI(), ((ConstraintViolationException) ex).getConstraintViolations()),
                HttpStatus.BAD_REQUEST);
    }

}
