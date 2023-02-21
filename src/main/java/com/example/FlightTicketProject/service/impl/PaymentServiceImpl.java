package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.exception.PaymentNotFoundException;
import com.example.FlightTicketProject.repository.PaymentRepository;
import com.example.FlightTicketProject.security.configuration.JwtAuthenticationFilter;
import com.example.FlightTicketProject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment with ID = " + paymentId + " will not be found"));
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void update(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> findAllByUser() {
        return paymentRepository.findAllByUser(JwtAuthenticationFilter.getCurrentUserEmail());
    }
}
