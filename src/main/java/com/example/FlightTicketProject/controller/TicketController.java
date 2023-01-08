package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.service.TicketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Test ticket controller")
@RequestMapping("/api/tickets")
@RestController
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public List<Ticket> getAllTickets() {
        return ticketService.findAll();
    }

    @PostMapping("/")
    public Ticket saveTicket(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
}
