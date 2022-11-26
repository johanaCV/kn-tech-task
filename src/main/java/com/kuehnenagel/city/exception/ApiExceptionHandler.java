package com.kuehnenagel.city.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CityNotFoundException.class})
    public ResponseEntity<ApiException> handleException(CityNotFoundException e) {
        ApiException apiException =
                new ApiException(e.getMessage(), CityNotFoundException.STATUS , LocalDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

}
