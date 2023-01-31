package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PaymentDTO {

    private long id;

    private double sum;

    private String owner;

    private Date date;

    private PaymentStatus paymentStatus;
}
