package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Test payment controller")
@RequestMapping("/api/payments")
@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public List<Payment> getAllPayments() {
        return paymentService.findAll();
    }

    @PostMapping("/")
    public Payment savePayment(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }
}
