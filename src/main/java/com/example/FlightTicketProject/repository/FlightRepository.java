package com.example.FlightTicketProject.repository;

import com.example.FlightTicketProject.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Modifying
    @Query(value = "DELETE FROM flights f WHERE NOT EXISTS (SELECT * FROM tickets t WHERE t.flight_id = f.id)", nativeQuery = true)
    void deleteAllUnrelated();

    @Query(value = "SELECT f.price FROM payments p JOIN tickets t ON p.id = t.payment_id JOIN flights f ON t.flight_id = f.id WHERE p.id = ?", nativeQuery = true)
    double findPriceFlightByPaymentId(long paymentId);

    @Query(value = "SELECT f.id, f.origin, f.destination, f.departure, f.arrival, f.price, f.fare_сlass, f.carrier FROM flights f JOIN tickets t ON f.id = t.flight_id JOIN users u ON u.id = t.user_id WHERE u.email = ?", nativeQuery = true)
    List<Flight> findAllByUser(String email);
}
