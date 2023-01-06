package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.exception.PaymentNotFoundException;
import com.example.FlightTicketProject.repository.PaymentRepository;
import com.example.FlightTicketProject.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException());
    }

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void update(Payment payment) {
        paymentRepository.save(payment);
    }
}
