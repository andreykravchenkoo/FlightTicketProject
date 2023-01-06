package com.example.FlightTicketProject.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "FLIGHTS")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ORIGIN")
    private String origin;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "DEPARTURE")
    private LocalDate departure;

    @Column(name = "ARRIVAL")
    private LocalDate arrival;

    @Column(name = "PRICE")
    private double price;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private final Set<Ticket> tickets = new HashSet<>();

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setFlight(this);
    }

    public boolean removeTicket(Ticket ticket) {
        ticket.setFlight(null);
        return tickets.remove(ticket);
    }
}
