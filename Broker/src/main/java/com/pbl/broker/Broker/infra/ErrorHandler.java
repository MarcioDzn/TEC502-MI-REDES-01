package com.pbl.broker.Broker.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<?> handler404() {
        return ResponseEntity.notFound().build();
    }
}
