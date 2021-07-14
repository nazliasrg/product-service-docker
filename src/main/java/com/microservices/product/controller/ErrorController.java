package com.microservices.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
