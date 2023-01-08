package com.example.FlightTicketProject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Payment(Ticket ticket, double price, LocalDate date) {
        this.ticket = ticket;
        this.price = price;
        this.date = date;
    }
}
