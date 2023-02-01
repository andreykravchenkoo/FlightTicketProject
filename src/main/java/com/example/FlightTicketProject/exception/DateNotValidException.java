package com.example.FlightTicketProject.exception;

public class DateNotValidException extends RuntimeException {

    public DateNotValidException() {
    }

    public DateNotValidException(String message) {
        super(message);
    }
}
