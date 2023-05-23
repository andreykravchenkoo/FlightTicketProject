package com.example.FlightTicketProject.exception;

public class ExternalApiParseException extends RuntimeException {

    public ExternalApiParseException() {}

    public ExternalApiParseException(String message) {
        super(message);
    }
}
