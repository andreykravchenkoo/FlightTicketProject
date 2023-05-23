package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.PaymentStatus;
import com.example.FlightTicketProject.exception.InvalidSumException;
import com.example.FlightTicketProject.exception.PaymentAlreadyExecuteException;
import com.example.FlightTicketProject.exception.UnauthorizedAccessException;
import com.example.FlightTicketProject.security.configuration.JwtAuthenticationFilter;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentProcessorFacade {

    private final FlightService flightService;

    private final PaymentService paymentService;

    public Payment executePayment(long paymentId, double sum) {
        log.info("Execute payment with this data: paymentId = {}, sum = {}", paymentId, sum);

        Payment payment = paymentService.findById(paymentId);

        checkOwner(payment.getTicket().getUser().getEmail());

        checkExecution(payment.getStatus());

        double priceFlight = flightService.findPriceFlightByPaymentId(paymentId);
        checkSum(sum, priceFlight);

        payment.setSum(sum);
        payment.setDate(new Date());
        payment.setStatus(PaymentStatus.DONE);

        return paymentService.save(payment);
    }

    private void checkExecution(PaymentStatus paymentStatus) {
        if (!paymentStatus.equals(PaymentStatus.NEW)) {
            log.error(
                    "Payment status is not 'New', throwing exception. Payment status = {}",
                    paymentStatus);
            throw new PaymentAlreadyExecuteException(
                    "Payment already execute or is in the archive");
        }
    }

    private void checkSum(double sum, double priceFlight) {
        if (sum != priceFlight) {
            log.error(
                    "Payment sum does not match the price of the flight. Sum = {}, priceFlight = {}",
                    sum,
                    priceFlight);
            throw new InvalidSumException(
                    "Invalid sum = " + sum + " because price of the flight = " + priceFlight);
        }
    }

    private void checkOwner(String email) {
        String currentUserEmail = JwtAuthenticationFilter.getCurrentUserEmail();
        if (!email.equals(currentUserEmail)) {
            log.error(
                    "User not authorized to execute payment. Owner payment = {}, current user = {}",
                    email,
                    currentUserEmail);
            throw new UnauthorizedAccessException("User not authorized to execute payment");
        }
    }
}
