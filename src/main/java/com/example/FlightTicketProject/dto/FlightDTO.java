package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDTO {

    private long id;

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

    public FlightDTO(ExternalApiFlightResponse externalApiFlightResponse) {
        this.origin = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getLegs().get(0).getOrigin().getName();
        this.destination = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getLegs().get(0).getDestination().getName();
        this.departure = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getLegs().get(0).getDeparture();
        this.arrival = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getLegs().get(0).getArrival();
        this.price = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getPrice().getRaw();
        this.carrier = externalApiFlightResponse.getData().getBuckets().get(0).getItems().get(0).getLegs().get(0).getCarriers().getMarketing().get(0).getName();
    }
}
