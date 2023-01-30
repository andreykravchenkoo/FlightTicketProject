package com.example.FlightTicketProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidSumException extends RuntimeException {

    public InvalidSumException() {
    }

    public InvalidSumException(String message) {
        super(message);
    }
}