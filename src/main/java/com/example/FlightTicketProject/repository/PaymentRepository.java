package com.example.FlightTicketProject.repository;

import com.example.FlightTicketProject.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT p.id, p.owner, p.sum, p.date, p.status FROM payments p JOIN tickets t ON p.id = t.payment_id JOIN users u ON u.id = t.user_id WHERE u.email = ?", nativeQuery = true)
    List<Payment> findAllByUser(String email);
}
