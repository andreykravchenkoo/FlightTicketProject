package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.Payment;

public interface PaymentProcessorFacade {
    Payment executePayment(long paymentId, double sum);
}
