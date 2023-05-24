package com.example.FlightTicketProject.facade.impl;

import com.example.FlightTicketProject.entity.*;
import com.example.FlightTicketProject.facade.BookingTicketFacade;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;
import com.example.FlightTicketProject.service.TicketService;
import com.example.FlightTicketProject.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookingTicketFacadeImpl implements BookingTicketFacade {

    private final FlightService flightService;

    private final TicketService ticketService;

    private final PaymentService paymentService;

    private final UserService userService;

    @Transactional
    @Override
    public Ticket bookTicket(String flightId, String seat) {
        log.info(
                "Booking ticket for flight with this data: flightId = {}, seat = {}",
                flightId,
                seat);

        Flight flight = flightService.findById(flightId);
        User user = userService.findByEmail();

        String owner = user.getFirstname() + " " + user.getLastname();

        Ticket ticket = new Ticket(owner, seat);
        Payment payment = new Payment(owner, new Date(), PaymentStatus.NEW);

        ticket.setFlight(flight);
        ticket.setPayment(payment);
        ticket.setUser(user);
        payment.setTicket(ticket);
        flight.addTicket(ticket);
        user.addTicket(ticket);

        ticketService.save(ticket);
        paymentService.save(payment);
        flightService.save(flight);
        userService.save(user);

        return ticket;
    }
}
