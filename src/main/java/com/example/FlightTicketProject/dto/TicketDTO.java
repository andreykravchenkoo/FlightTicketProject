package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDTO {

    private long id;

    private String owner;

    private String seat;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.owner = ticket.getOwner();
        this.seat = ticket.getSeat();
    }
}
