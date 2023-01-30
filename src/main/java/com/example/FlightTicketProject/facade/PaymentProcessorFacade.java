package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.PaymentStatus;
import com.example.FlightTicketProject.exception.InvalidSumException;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class PaymentProcessorFacade {

    private final FlightService flightService;

    private final PaymentService paymentService;

    public Payment executePayment(long paymentId, double sum) {
        Payment payment = paymentService.findById(paymentId);

        double priceFlight = flightService.findPriceFlightByPaymentId(paymentId);

        checkSum(sum, priceFlight);

        payment.setSum(sum);
        payment.setDate(new Date());
        payment.setStatus(PaymentStatus.DONE);

        return paymentService.save(payment);
    }

    private void checkSum(double sum, double priceFlight) {
        if (sum < priceFlight || sum > priceFlight) {
            throw new InvalidSumException("Invalid sum = " + sum + "because price of the flight = " + priceFlight);
        }
    }
}
