package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
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

    private EntityMapper entityMapper;

    @Autowired
    public TicketController(TicketService ticketService, EntityMapper entityMapper) {
        this.ticketService = ticketService;
        this.entityMapper = entityMapper;
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

        return new ResponseEntity<>(new TicketDTO(ticketById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TicketDTO> saveTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = entityMapper.mapTicketDTOToEntity(ticketDTO);

        Ticket savedTicket = ticketService.save(ticket);

        return new ResponseEntity<>(new TicketDTO(savedTicket), HttpStatus.CREATED);
    }
}
