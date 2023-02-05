package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.FareClassStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDTO {


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
}
