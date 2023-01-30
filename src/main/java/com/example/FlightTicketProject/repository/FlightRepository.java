package com.example.FlightTicketProject.repository;

import com.example.FlightTicketProject.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Modifying
    @Query(value = "DELETE FROM flights f WHERE NOT EXISTS (SELECT * FROM tickets t WHERE t.flight_id = f.id)", nativeQuery = true)
    void deleteAllUnrelated();

    @Query(value = "SELECT flights.price FROM payments JOIN tickets ON payments.id = tickets.payment_id JOIN flights ON tickets.flight_id = flights.id WHERE payments.id = ?", nativeQuery = true)
    double findPriceFlightByPaymentId(long paymentId);
}
