package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Flight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FlightDTO {

    private long id;

    private String origin;

    private String destination;

    private LocalDate departure;

    private LocalDate arrival;

    private double price;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.origin = flight.getOrigin();
        this.destination = flight.getDestination();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.price = flight.getPrice();
    }
}
