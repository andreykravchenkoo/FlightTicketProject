package com.example.FlightTicketProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketInfoWithPaymentIdDto {

    private long ticketId;

    private String owner;

    private String seat;

    private long paymentId;
}
