package com.example.FlightTicketProject.exception;

public class InvalidSumException extends RuntimeException {

    public InvalidSumException() {
    }

    public InvalidSumException(String message) {
        super(message);
    }
}