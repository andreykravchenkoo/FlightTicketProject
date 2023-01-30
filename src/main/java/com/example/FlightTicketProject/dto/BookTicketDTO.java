package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookTicketDTO {

    private String flightId;

    private String owner;

    private String seat;
}
