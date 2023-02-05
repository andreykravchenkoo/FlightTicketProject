package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.BookTicketDTO;
import com.example.FlightTicketProject.dto.TicketInfoWIthPaymentIdDTO;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.facade.BookingTicketFacade;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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
public class BookingController {

    private final BookingTicketFacade bookingTicketFacade;

    @PostMapping("/{flightId}")
    public ResponseEntity<TicketInfoWIthPaymentIdDTO> bookTicket(@RequestBody @Valid BookTicketDTO bookTicketDTO) {
        Ticket ticket = bookingTicketFacade.bookTicket(bookTicketDTO.getFlightId(),
                bookTicketDTO.getOwner(),
                bookTicketDTO.getSeat());

        return new ResponseEntity<>(EntityDTOMapper.mapTicketAndPaymentIdToTicketInfoWIthPaymentIdDTO(ticket, ticket.getPayment().getId()), HttpStatus.CREATED);
    }
}
