package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.exception.TicketNotFoundException;
import com.example.FlightTicketProject.repository.TicketRepository;
import com.example.FlightTicketProject.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException());
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
