package com.example.feedservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
    }
}
