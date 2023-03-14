package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.BookTicketDto;
import com.example.FlightTicketProject.dto.TicketInfoWithPaymentIdDto;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.facade.BookingTicketFacade;
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
@Api(tags = "Booking controller")
@RequestMapping("/api/booking")
@RestController
@Slf4j
public class BookingController {

    private final BookingTicketFacade bookingTicketFacade;

    @PostMapping
    public ResponseEntity<TicketInfoWithPaymentIdDto> bookTicket(@RequestBody @Valid BookTicketDto bookTicketDto) {
        log.info("Received request to book a ticket for flight with id = {}", bookTicketDto.getFlightId());

        Ticket ticket = bookingTicketFacade.bookTicket(bookTicketDto.getFlightId(), bookTicketDto.getSeat());

        log.info("Ticket booking successful with id = {}", ticket.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket.toDtoWithPaymentId(ticket.getPayment().getId()));
    }
}
