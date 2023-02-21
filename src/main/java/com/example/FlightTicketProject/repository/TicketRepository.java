package com.example.FlightTicketProject.repository;

import com.example.FlightTicketProject.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT t.id, t.owner, t.seat, t.payment_id, t.user_id, t.flight_id FROM tickets t, users u WHERE t.user_id = u.id AND u.email = ?", nativeQuery = true)
    List<Ticket> findAllByUser(String email);
}
