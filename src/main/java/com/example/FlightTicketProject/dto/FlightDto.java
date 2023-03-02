package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.FareClassStatus;

import com.example.FlightTicketProject.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDto {

    @NotBlank(message = "ID is required")
    private String id;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure date is required")
    private Date departure;

    @NotNull(message = "Arrival date is required")
    private Date arrival;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    @NotNull(message = "Fare Class is required")
    private FareClassStatus fareClass;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    public FlightDto(String id, String origin, String destination, Date departure, Date arrival, double price, String carrier) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.carrier = carrier;
    }

    public Flight toEntity() {
        return Flight.builder()
                .id(this.id)
                .origin(this.origin)
                .destination(this.destination)
                .departure(this.departure)
                .arrival(this.arrival)
                .price(this.price)
                .fareClass(this.fareClass)
                .carrier(this.carrier)
                .build();
    }
}
