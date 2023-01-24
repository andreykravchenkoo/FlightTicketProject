package com.example.FlightTicketProject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
public class Flight {

    @Id
    private String id;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure")
    private Date departure;

    @Column(name = "arrival")
    private Date arrival;

    @Column(name = "price")
    private double price;

    @Column(name = "carrier")
    private String carrier;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Flight(String origin, String destination, Date departure, Date arrival, double price, String carrier) {
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.carrier = carrier;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setFlight(this);
    }

    public boolean removeTicket(Ticket ticket) {
        ticket.setFlight(null);
        return tickets.remove(ticket);
    }
}
