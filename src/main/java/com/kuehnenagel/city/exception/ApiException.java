package com.kuehnenagel.city.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {

    private final String message;
    private HttpStatus status;
    private final LocalDateTime timestamp;

    public ApiException(String message, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
