package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket findById(Long ticketId);

    Ticket save(Ticket ticket);

    void update(Ticket ticket);
}
