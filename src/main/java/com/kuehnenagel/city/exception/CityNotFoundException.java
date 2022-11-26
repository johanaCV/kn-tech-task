package com.kuehnenagel.city.exception;

import org.springframework.http.HttpStatus;

public class CityNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "City not found";
    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public CityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
