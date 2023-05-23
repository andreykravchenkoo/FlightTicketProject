package com.example.FlightTicketProject.entity;

import com.example.FlightTicketProject.dto.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "sum")
    private double sum;

    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private Ticket ticket;

    public Payment(String owner, Date date, PaymentStatus status) {
        this.owner = owner;
        this.date = date;
        this.status = status;
    }

    public PaymentDto toDto() {
        return PaymentDto.builder()
                .id(this.id)
                .owner(this.owner)
                .sum(this.sum)
                .date(this.date)
                .status(this.status)
                .build();
    }
}
