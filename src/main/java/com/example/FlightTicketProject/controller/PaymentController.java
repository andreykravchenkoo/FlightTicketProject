package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.PaymentDTO;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.service.PaymentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("Test payment controller")
@RequestMapping("/api/payments")
@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("")
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentService.findAll();

        return payments.stream()
                .map(PaymentDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable long paymentId) {
        Payment paymentById = paymentService.findById(paymentId);

        return new ResponseEntity<>(convertToDTO(paymentById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);

        Payment savedPayment = paymentService.save(payment);

        return new ResponseEntity<>(convertToDTO(savedPayment), HttpStatus.CREATED);
    }

    private Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setId(paymentDTO.getId());
        payment.setOwner(paymentDTO.getOwner());
        payment.setSum(paymentDTO.getSum());
        payment.setDate(paymentDTO.getDate());
        payment.setStatus(paymentDTO.getPaymentStatus());

        return payment;
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(payment);
    }
}
