package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TicketDTO {

    @NotNull(message = "ID is required")
    private long id;

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotBlank(message = "Seat is required")
    private String seat;
}
