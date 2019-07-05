package com.powerreviews.project.controller.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RestaurantNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestaurantNameConstraint {
    String message() default "Invalid restaurant name or it does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}