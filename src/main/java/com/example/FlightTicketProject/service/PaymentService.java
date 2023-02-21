package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.entity.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment findById(Long paymentId);

    Payment save(Payment payment);

    void update(Payment payment);

    List<Payment> findAllByUser();
}
