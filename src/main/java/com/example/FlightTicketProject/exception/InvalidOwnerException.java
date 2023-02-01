package com.example.FlightTicketProject.exception;

public class InvalidOwnerException extends RuntimeException {

    public InvalidOwnerException() {
    }

    public InvalidOwnerException(String message) {
        super(message);
    }
}