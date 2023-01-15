package com.example.FlightTicketProject.mapper;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.dto.PaymentDTO;
import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.entity.User;

import java.util.function.Function;

public class EntityMapper {

    private final Function<FlightDTO, Flight> processMapFlight = (flightDTO) -> {
        Flight flight = new Flight();

        flight.setId(flightDTO.getId());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDeparture(flightDTO.getDeparture());
        flight.setArrival(flightDTO.getArrival());
        flight.setPrice(flightDTO.getPrice());

        return flight;
    };

    private final Function<PaymentDTO, Payment> processMapPayment = (paymentDTO) -> {
        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setOwner(paymentDTO.getOwner());
        payment.setSum(paymentDTO.getSum());
        payment.setDate(paymentDTO.getDate());
        payment.setStatus(paymentDTO.getPaymentStatus());

        return payment;
    };

    private final Function<TicketDTO, Ticket> processMapTicket = (ticketDTO) -> {
        Ticket ticket = new Ticket();

        ticket.setId(ticketDTO.getId());
        ticket.setOwner(ticketDTO.getOwner());
        ticket.setSeat(ticketDTO.getSeat());
        ticket.setStatus(ticketDTO.getStatus());

        return ticket;
    };

    private final Function<UserDTO, User> processMapUser = (userDTO) -> {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    };

    public Flight mapFlightDTOToEntity(FlightDTO flightDTO) {
        return processMapFlight.apply(flightDTO);
    }

    public Payment mapPaymentDTOToEntity(PaymentDTO paymentDTO) {
        return processMapPayment.apply(paymentDTO);
    }

    public Ticket mapTicketDTOToEntity(TicketDTO ticketDTO) {
        return processMapTicket.apply(ticketDTO);
    }

    public User mapUserDTOToEntity(UserDTO userDTO) {
        return processMapUser.apply(userDTO);
    }
}
