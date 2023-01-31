package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketInfoWIthPaymentIdDTO {
    private long ticketId;

    private String owner;

    private String seat;

    private long paymentId;
}
