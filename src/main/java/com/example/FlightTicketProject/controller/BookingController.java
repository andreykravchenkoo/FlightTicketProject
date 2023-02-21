package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.BookTicketDTO;
import com.example.FlightTicketProject.dto.TicketInfoWIthPaymentIdDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.facade.BookingTicketFacade;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "Test booking controller")
@RequestMapping("/api/booking")
@RestController
@Slf4j
public class BookingController {

    private final BookingTicketFacade bookingTicketFacade;

    @PostMapping("/{flightId}")
    public ResponseEntity<TicketInfoWIthPaymentIdDTO> bookTicket(@RequestBody @Valid BookTicketDTO bookTicketDTO) {
        log.info("Received request to book a ticket for flight with id = {}", bookTicketDTO.getFlightId());

        Ticket ticket = bookingTicketFacade.bookTicket(bookTicketDTO.getFlightId(), bookTicketDTO.getSeat());

        log.info("Ticket booking successful with id = {}", ticket.getId());
        return new ResponseEntity<>(EntityDTOMapper.mapTicketAndPaymentIdToTicketInfoWIthPaymentIdDTO(ticket, ticket.getPayment().getId()), HttpStatus.CREATED);
    }
}
