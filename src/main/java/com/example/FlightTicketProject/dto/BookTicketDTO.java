package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BookTicketDTO {

    @NotBlank(message = "ID is required")
    private String flightId;

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotBlank(message = "Seat is required")
    private String seat;
}
