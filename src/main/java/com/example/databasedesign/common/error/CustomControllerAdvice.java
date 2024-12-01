package com.example.databasedesign.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomHttpExceptionResponse> handleRuntimeException(RuntimeException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(badRequest)
                .body(new CustomHttpExceptionResponse(e.getMessage(), badRequest.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomHttpExceptionResponse> handleException(Exception e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(internalServerError)
                .body(new CustomHttpExceptionResponse(e.getMessage(), internalServerError.value()));
    }
}
