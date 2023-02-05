package com.example.FlightTicketProject.exception;

public class PaymentAlreadyExecuteException extends RuntimeException {

    public PaymentAlreadyExecuteException() {
    }

    public PaymentAlreadyExecuteException(String message) {
        super(message);
    }
}
