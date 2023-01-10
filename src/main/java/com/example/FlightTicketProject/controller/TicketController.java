package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.service.TicketService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("Test ticket controller")
@RequestMapping("/api/tickets")
@RestController
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("")
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketService.findAll();

        return tickets.stream()
                .map(TicketDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable long ticketId) {
        Ticket ticketById = ticketService.findById(ticketId);

        return new ResponseEntity<>(convertToDTO(ticketById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TicketDTO> saveTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);

        Ticket savedTicket = ticketService.save(ticket);

        return new ResponseEntity<>(convertToDTO(savedTicket), HttpStatus.CREATED);
    }

    private Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();

        ticket.setId(ticketDTO.getId());
        ticket.setOwner(ticketDTO.getOwner());
        ticket.setSeat(ticketDTO.getSeat());
        ticket.setStatus(ticketDTO.getStatus());

        return ticket;
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        return new TicketDTO(ticket);
    }
}
