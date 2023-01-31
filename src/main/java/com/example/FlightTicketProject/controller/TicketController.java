package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
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

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        List<TicketDTO> ticketsDTO = ticketService.findAll().stream()
                .map(EntityDTOMapper::mapTicketToTicketDTO)
                .toList();

        return new ResponseEntity<>(ticketsDTO, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable long ticketId) {
        Ticket ticketById = ticketService.findById(ticketId);

        return new ResponseEntity<>(EntityDTOMapper.mapTicketToTicketDTO(ticketById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> saveTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = EntityDTOMapper.mapTicketDTOToEntity(ticketDTO);

        Ticket savedTicket = ticketService.save(ticket);

        return new ResponseEntity<>(EntityDTOMapper.mapTicketToTicketDTO(savedTicket), HttpStatus.CREATED);
    }
}
