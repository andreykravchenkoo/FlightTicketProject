package com.example.FlightTicketProject.mapper;

import com.example.FlightTicketProject.dto.*;
import com.example.FlightTicketProject.entity.*;
import com.example.FlightTicketProject.dto.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.dto.response.ExternalApiFlightResponse;

public class EntityDTOMapper {

    public static Flight mapFlightDTOToEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();

        flight.setId(flightDTO.getId());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDeparture(flightDTO.getDeparture());
        flight.setArrival(flightDTO.getArrival());
        flight.setPrice(flightDTO.getPrice());
        flight.setFareClass(flightDTO.getFareClass());
        flight.setCarrier(flightDTO.getCarrier());

        return flight;
    }

    public static FlightDTO mapFlightToFlightDTO(Flight flight) {
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(flight.getId());
        flightDTO.setOrigin(flight.getOrigin());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setDeparture(flight.getDeparture());
        flightDTO.setArrival(flight.getArrival());
        flightDTO.setPrice(flight.getPrice());
        flightDTO.setFareClass(flight.getFareClass());
        flightDTO.setCarrier(flight.getCarrier());

        return flightDTO;
    }

    public static FlightDTO mapExternalApiFlightResponseToFLightDTO(ExternalApiFlightResponse.Item item) {
        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId(item.getId());
        flightDTO.setOrigin(item.getLegs().get(0).getOrigin().getName());
        flightDTO.setDestination(item.getLegs().get(0).getDestination().getName());
        flightDTO.setDeparture(item.getLegs().get(0).getDeparture());
        flightDTO.setArrival(item.getLegs().get(0).getArrival());
        flightDTO.setPrice(item.getPrice().getRaw());
        flightDTO.setFareClass(FareClassStatus.valueOf(item.getFareClass().toUpperCase()));
        flightDTO.setCarrier(item.getLegs().get(0).getCarriers().getMarketing().get(0).getName());

        return flightDTO;
    }

    public static Payment mapPaymentDTOToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setOwner(paymentDTO.getOwner());
        payment.setSum(paymentDTO.getSum());
        payment.setDate(paymentDTO.getDate());
        payment.setStatus(paymentDTO.getPaymentStatus());

        return payment;
    }

    public static PaymentDTO mapPaymentToPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setId(payment.getId());
        paymentDTO.setSum(payment.getSum());
        paymentDTO.setOwner(payment.getOwner());
        paymentDTO.setDate(payment.getDate());
        paymentDTO.setPaymentStatus(payment.getStatus());

        return paymentDTO;
    }

    public static Ticket mapTicketDTOToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();

        ticket.setId(ticketDTO.getId());
        ticket.setOwner(ticketDTO.getOwner());
        ticket.setSeat(ticketDTO.getSeat());

        return ticket;
    }

    public static TicketDTO mapTicketToTicketDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId(ticket.getId());
        ticketDTO.setOwner(ticket.getOwner());
        ticketDTO.setSeat(ticket.getSeat());

        return ticketDTO;
    }

    public static User mapUserDTOToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        return user;
    }

    public static UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public static AirportInfoDTO mapExternalApiAirportResponseToAirportInfoDTO(ExternalApiAirportResponse.Data data) {
        AirportInfoDTO airportInfoDTO = new AirportInfoDTO();

        airportInfoDTO.setIATACode(data.getIata_code());
        airportInfoDTO.setAirportName(data.getName());
        airportInfoDTO.setCity(data.getCity());
        airportInfoDTO.setCountry(data.getCountry());

        return airportInfoDTO;
    }

    public static TicketInfoWIthPaymentIdDTO mapTicketAndPaymentIdToTicketInfoWIthPaymentIdDTO(Ticket ticket, long paymentId) {
        TicketInfoWIthPaymentIdDTO ticketInfoWIthPaymentIdDTO = new TicketInfoWIthPaymentIdDTO();

        ticketInfoWIthPaymentIdDTO.setTicketId(ticket.getId());
        ticketInfoWIthPaymentIdDTO.setOwner(ticket.getOwner());
        ticketInfoWIthPaymentIdDTO.setSeat(ticket.getSeat());
        ticketInfoWIthPaymentIdDTO.setPaymentId(paymentId);

        return ticketInfoWIthPaymentIdDTO;
    }
}
