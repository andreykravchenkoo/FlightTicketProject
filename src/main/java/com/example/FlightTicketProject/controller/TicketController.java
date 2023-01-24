package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
import com.example.FlightTicketProject.service.TicketService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api("Test ticket controller")
@RequestMapping("/api/tickets")
@RestController
public class TicketController {

    private final TicketService ticketService;

    private final EntityMapper entityMapper;

    @GetMapping("/search-all-saved")
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketService.findAll();

        return tickets.stream()
                .map(TicketDTO::new)
                .toList();
    }

    @GetMapping("/search-saved")
    public ResponseEntity<TicketDTO> getTicketById(@RequestParam long ticketId) {
        Ticket ticketById = ticketService.findById(ticketId);

        return new ResponseEntity<>(new TicketDTO(ticketById), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<TicketDTO> saveTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = entityMapper.mapTicketDTOToEntity(ticketDTO);

        Ticket savedTicket = ticketService.save(ticket);

        return new ResponseEntity<>(new TicketDTO(savedTicket), HttpStatus.CREATED);
    }
}
