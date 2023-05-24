package com.example.FlightTicketProject.facade;

import com.example.FlightTicketProject.entity.Ticket;

public interface BookingTicketFacade {
    Ticket bookTicket(String flightId, String seat);
}
