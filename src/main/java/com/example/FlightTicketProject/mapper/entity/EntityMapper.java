package com.example.FlightTicketProject.mapper.entity;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.dto.PaymentDTO;
import com.example.FlightTicketProject.dto.TicketDTO;
import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.entity.User;

public class EntityMapper {

    public Flight mapFlightDTOToEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();

        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDeparture(flightDTO.getDeparture());
        flight.setArrival(flightDTO.getArrival());
        flight.setPrice(flightDTO.getPrice());

        return flight;
    }

    public Payment mapPaymentDTOToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setOwner(paymentDTO.getOwner());
        payment.setSum(paymentDTO.getSum());
        payment.setDate(paymentDTO.getDate());
        payment.setStatus(paymentDTO.getPaymentStatus());

        return payment;
    }

    public Ticket mapTicketDTOToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();

        ticket.setId(ticketDTO.getId());
        ticket.setOwner(ticketDTO.getOwner());
        ticket.setSeat(ticketDTO.getSeat());
        ticket.setStatus(ticketDTO.getStatus());

        return ticket;
    }

    public User mapUserDTOToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }
}
