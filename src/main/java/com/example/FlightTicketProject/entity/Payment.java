package com.example.FlightTicketProject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "payment")
    private Ticket ticket;

    @Column(name = "price")
    private double price;

    @Column(name = "date")
    private LocalDate date;
}
