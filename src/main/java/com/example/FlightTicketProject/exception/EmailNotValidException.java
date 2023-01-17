package com.example.FlightTicketProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailNotValidException extends RuntimeException {

    public EmailNotValidException() {
    }

    public EmailNotValidException(String message) {
        super(message);
    }
}
