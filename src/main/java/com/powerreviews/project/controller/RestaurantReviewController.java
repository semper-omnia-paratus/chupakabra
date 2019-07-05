package com.powerreviews.project.controller;

import com.powerreviews.project.controller.dto.ErrorInfo;
import com.powerreviews.project.controller.dto.RestaurantReviewDto;
import com.powerreviews.project.service.RestaurantNotFoundException;
import com.powerreviews.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantReviewController {
    private final ReviewService reviewService;

    @Autowired
    public RestaurantReviewController(ReviewService rs) {
        this.reviewService = rs;
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RestaurantReviewDto> get(@NotNull @PathVariable Integer id) {
        RestaurantReviewDto restaurant = reviewService.retrieveReview(id);
        return new ResponseEntity<>(restaurant, new HttpHeaders(), restaurant == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestaurantReviewDto> post(@Valid @RequestBody RestaurantReviewDto review) throws RestaurantNotFoundException {
        return new ResponseEntity<>(reviewService.addReview(review), new HttpHeaders(), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseEntity<ErrorInfo> handleConstraintViolation(Exception ex, ServletWebRequest req) {
        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException) {
                return new ResponseEntity<>(
                        new ErrorInfo(req.getRequest().getRequestURI(), ((ConstraintViolationException) cause).getConstraintViolations()),
                        HttpStatus.BAD_REQUEST);
            }
            cause = cause.getCause();
        }
        return null;
    }

}
