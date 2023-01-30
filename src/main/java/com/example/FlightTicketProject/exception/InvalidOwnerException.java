package com.example.FlightTicketProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidOwnerException extends RuntimeException {

    public InvalidOwnerException() {
    }

    public InvalidOwnerException(String message) {
        super(message);
    }
}