package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDTO {

    private long id;

    private String owner;

    private String seat;
}
