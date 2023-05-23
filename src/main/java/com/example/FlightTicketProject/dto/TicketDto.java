package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDto {

    @NotNull(message = "ID is required")
    private long id;

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotBlank(message = "Seat is required")
    private String seat;

    public Ticket toEntity() {
        return Ticket.builder().id(this.id).owner(this.owner).seat(this.seat).build();
    }
}
