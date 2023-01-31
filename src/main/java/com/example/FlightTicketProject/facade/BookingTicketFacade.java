package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.*;
import com.example.FlightTicketProject.exception.InvalidOwnerException;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;
import com.example.FlightTicketProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class BookingTicketFacade {

    private final FlightService flightService;

    private final TicketService ticketService;

    private final PaymentService paymentService;

    @Transactional
    public Ticket bookTicket(String flightId, String owner, String seat) {
        Flight flight = flightService.findById(flightId);

        checkOwner(owner);

        Ticket ticket = new Ticket(owner, seat);
        Payment payment = new Payment(owner, new Date(), PaymentStatus.NEW);

        ticket.setFlight(flight);
        ticket.setPayment(payment);
        payment.setTicket(ticket);
        flight.addTicket(ticket);

        ticketService.save(ticket);
        paymentService.save(payment);
        flightService.save(flight);

        return ticket;
    }

    private void checkOwner(String owner) {
        if (owner.isEmpty()) {
            throw new InvalidOwnerException("Field name is empty");
        }
    }
}
