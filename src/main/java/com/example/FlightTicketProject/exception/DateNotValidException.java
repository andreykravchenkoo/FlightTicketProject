package com.example.FlightTicketProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DateNotValidException extends RuntimeException {

    public DateNotValidException() {
    }

    public DateNotValidException(String message) {
        super(message);
    }
}
