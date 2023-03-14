package com.example.FlightTicketProject.entity;

import com.example.FlightTicketProject.dto.FlightDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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

    @Enumerated(EnumType.STRING)
    @Column(name = "fare_class")
    private FareClassStatus fareClass;

    @Column(name = "carrier")
    private String carrier;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    public Flight(String origin, String destination, Date departure, Date arrival, double price, FareClassStatus fareClass, String carrier) {
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.price = price;
        this.fareClass = fareClass;
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

    public FlightDto toDto() {
        return FlightDto.builder()
                .id(this.id)
                .origin(this.origin)
                .destination(this.destination)
                .departure(this.departure)
                .arrival(this.arrival)
                .price(this.price)
                .fareClass(this.fareClass)
                .carrier(this.carrier)
                .build();
    }
}
