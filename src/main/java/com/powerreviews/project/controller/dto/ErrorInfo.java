package com.powerreviews.project.controller.dto;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

public class ErrorInfo {
    public String url;
    public String ex;
    public Set<String> violations;

    public ErrorInfo() {
    }

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }

    public ErrorInfo(String url, Set<ConstraintViolation<?>> constraintViolations) {
        this.url = url;
        this.violations = new HashSet<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            violations.add(constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
        }
    }
}