package com.example.FlightTicketProject.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException() {
    }

    public EmailAlreadyTakenException(String message) {
        super(message);
    }
}
