package com.example.FlightTicketProject.entity;

import com.example.FlightTicketProject.dto.TicketDto;
import com.example.FlightTicketProject.dto.TicketInfoWithPaymentIdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "seat")
    private String seat;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    public Ticket(String owner, String seat) {
        this.owner = owner;
        this.seat = seat;
    }

    public TicketDto toDto() {
        return TicketDto.builder()
                .id(this.id)
                .owner(this.owner)
                .seat(this.seat)
                .build();
    }

    public TicketInfoWithPaymentIdDto toDtoWithPaymentId(long paymentId) {
        return TicketInfoWithPaymentIdDto.builder()
                .ticketId(this.id)
                .owner(this.owner)
                .seat(this.seat)
                .paymentId(paymentId)
                .build();
    }
}
