package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketInfoWIthPaymentIdDTO {
    private long ticketId;

    private String owner;

    private String seat;

    private long paymentId;

    public TicketInfoWIthPaymentIdDTO(Ticket ticket, long paymentId) {
        this.ticketId = ticket.getId();
        this.owner = ticket.getOwner();
        this.seat = ticket.getSeat();
        this.paymentId = paymentId;
    }
}
