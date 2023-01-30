package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Payment;
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

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.sum = payment.getSum();
        this.owner = payment.getOwner();
        this.date = payment.getDate();
        this.paymentStatus = payment.getStatus();
    }
}
