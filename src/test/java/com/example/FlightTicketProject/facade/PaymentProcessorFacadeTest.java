package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.PaymentStatus;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.exception.InvalidSumException;
import com.example.FlightTicketProject.exception.PaymentAlreadyExecuteException;
import com.example.FlightTicketProject.exception.UnauthorizedAccessException;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorFacadeTest {

    @Mock
    private FlightService flightService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PaymentProcessorFacade paymentProcessorFacade;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testShouldSuccessfulyExecutePayment() {
        long expectedPaymentId = 1L;
        double expectedSum = 100;
        double expectedFlightPrice = 100;

        User expectedUser = new User();
        expectedUser.setEmail("email@gmail.com");

        Ticket expectedTicket = new Ticket();
        expectedTicket.setUser(expectedUser);

        Payment expectedPayment = new Payment();
        expectedPayment.setId(expectedPaymentId);
        expectedPayment.setSum(expectedSum);
        expectedPayment.setDate(new Date());
        expectedPayment.setStatus(PaymentStatus.NEW);
        expectedPayment.setTicket(expectedTicket);

        when(paymentService.findById(expectedPaymentId)).thenReturn(expectedPayment);
        when(flightService.findPriceFlightByPaymentId(expectedPaymentId)).thenReturn(expectedFlightPrice);
        when(authentication.getName()).thenReturn(expectedUser.getEmail());
        when(paymentService.save(expectedPayment)).thenReturn(expectedPayment);

        Payment payment = paymentProcessorFacade.executePayment(expectedPaymentId, expectedSum);

        assertAll(
                () -> assertEquals(PaymentStatus.DONE, payment.getStatus()),
                () -> assertEquals(expectedSum, payment.getSum()),
                () -> assertEquals(expectedUser.getEmail(), payment.getTicket().getUser().getEmail())

        );
    }

    @Test
    void testShouldFailExecutePaymentWhenUserUnauthorized() {
        long expectedPaymentId = 1L;
        double expectedSum = 100;
        String expectedErrorMessage = "User not authorized to execute payment";

        User expectedUser = new User();
        expectedUser.setEmail("email@gmail.com");

        Ticket expectedTicket = new Ticket();
        expectedTicket.setUser(expectedUser);

        Payment expectedPayment = new Payment();
        expectedPayment.setId(expectedPaymentId);
        expectedPayment.setSum(expectedSum);
        expectedPayment.setDate(new Date());
        expectedPayment.setStatus(PaymentStatus.NEW);
        expectedPayment.setTicket(expectedTicket);

        when(paymentService.findById(expectedPaymentId)).thenReturn(expectedPayment);
        when(authentication.getName()).thenThrow(new UnauthorizedAccessException(expectedErrorMessage));

        UnauthorizedAccessException exception = assertThrows(UnauthorizedAccessException.class, () -> paymentProcessorFacade.executePayment(expectedPaymentId, expectedSum));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());
    }

    @Test
    void testShouldFailExecutePaymentWhenExecutionIsNotNew() {
        long expectedPaymentId = 1L;
        double expectedSum = 100;
        String expectedErrorMessage = "Payment already execute or is in the archive";

        User expectedUser = new User();
        expectedUser.setEmail("email@gmail.com");

        Ticket expectedTicket = new Ticket();
        expectedTicket.setUser(expectedUser);

        Payment expectedPayment = new Payment();
        expectedPayment.setId(expectedPaymentId);
        expectedPayment.setSum(expectedSum);
        expectedPayment.setDate(new Date());
        expectedPayment.setStatus(PaymentStatus.ARCHIVE);
        expectedPayment.setTicket(expectedTicket);

        when(paymentService.findById(expectedPaymentId)).thenReturn(expectedPayment);
        when(authentication.getName()).thenReturn(expectedUser.getEmail());

        PaymentAlreadyExecuteException exception = assertThrows(PaymentAlreadyExecuteException.class, () -> paymentProcessorFacade.executePayment(expectedPaymentId, expectedSum));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());
    }

    @Test
    void testShouldFailExecutePaymentWhenSumIsInvalid() {
        long expectedPaymentId = 1L;
        double expectedSum = 99;
        double expectedFlightPrice = 100;
        String expectedErrorMessage = "Invalid sum = " + expectedSum + " because price of the flight = " + expectedFlightPrice;

        User expectedUser = new User();
        expectedUser.setEmail("email@gmail.com");

        Ticket expectedTicket = new Ticket();
        expectedTicket.setUser(expectedUser);

        Payment expectedPayment = new Payment();
        expectedPayment.setId(expectedPaymentId);
        expectedPayment.setSum(expectedSum);
        expectedPayment.setDate(new Date());
        expectedPayment.setStatus(PaymentStatus.NEW);
        expectedPayment.setTicket(expectedTicket);

        when(paymentService.findById(expectedPaymentId)).thenReturn(expectedPayment);
        when(authentication.getName()).thenReturn(expectedUser.getEmail());
        when(flightService.findPriceFlightByPaymentId(expectedPaymentId)).thenReturn(expectedFlightPrice);

        InvalidSumException exception = assertThrows(InvalidSumException.class, () -> paymentProcessorFacade.executePayment(expectedPaymentId, expectedSum));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());
    }
}