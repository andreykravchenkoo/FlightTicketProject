package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.exception.TicketNotFoundException;
import com.example.FlightTicketProject.repository.TicketRepository;
import com.example.FlightTicketProject.security.configuration.JwtAuthenticationFilter;
import com.example.FlightTicketProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket with ID = " + ticketId + " will not be found"));
    }

    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAllByUser() {
        return ticketRepository.findAllByUser(JwtAuthenticationFilter.getCurrentUserEmail());
    }
}
