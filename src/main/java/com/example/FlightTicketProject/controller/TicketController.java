package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDto;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.service.TicketService;

import io.swagger.annotations.Api;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@Api(tags = "Ticket controller")
@RequestMapping("/api/tickets")
@RestController
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> ticketsDto = ticketService.findAll().stream().map(Ticket::toDto).toList();

        return ResponseEntity.ok(ticketsDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TicketDto>> getAllTicketsByUser() {
        List<TicketDto> ticketsDto =
                ticketService.findAllByUser().stream().map(Ticket::toDto).toList();

        return ResponseEntity.ok(ticketsDto);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable @Min(1) long ticketId) {
        Ticket ticketById = ticketService.findById(ticketId);

        return ResponseEntity.ok(ticketById.toDto());
    }

    @PostMapping
    public ResponseEntity<TicketDto> saveTicket(@RequestBody @Valid TicketDto ticketDto) {
        Ticket ticket = ticketDto.toEntity();

        Ticket savedTicket = ticketService.save(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket.toDto());
    }
}
