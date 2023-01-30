package com.example.FlightTicketProject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
