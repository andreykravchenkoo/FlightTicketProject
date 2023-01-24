package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDTO {

    private String id;

    private String origin;

    private String destination;

    private Date departure;

    private Date arrival;

    private double price;

    private String carrier;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.origin = flight.getOrigin();
        this.destination = flight.getDestination();
        this.departure = flight.getDeparture();
        this.arrival = flight.getArrival();
        this.price = flight.getPrice();
    }

    public FlightDTO(ExternalApiFlightResponse.Item item) {
        this.id = item.getId();
        this.origin = item.getLegs().get(0).getOrigin().getName();
        this.destination = item.getLegs().get(0).getDestination().getName();
        this.departure = item.getLegs().get(0).getDeparture();
        this.arrival = item.getLegs().get(0).getArrival();
        this.price = item.getPrice().getRaw();
        this.carrier = item.getLegs().get(0).getCarriers().getMarketing().get(0).getName();
    }
}
