package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Flight;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightDTO {

    private final long id;

    private final String origin;

    private final String destination;

    private final LocalDate departure;

    private final LocalDate arrival;

    private final double price;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.origin = flight.getOrigin();
        this.destination = flight.getDestination();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.price = flight.getPrice();
    }
}
