package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.PaymentDto;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.facade.PaymentProcessorFacade;
import com.example.FlightTicketProject.service.PaymentService;

import io.swagger.annotations.Api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@Api(tags = "Payment controller")
@RequestMapping("/api/payments")
@RestController
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentProcessorFacade paymentProcessorFacade;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> paymentsDto =
                paymentService.findAll().stream().map(Payment::toDto).toList();

        return ResponseEntity.ok(paymentsDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsByUser() {
        List<PaymentDto> paymentsDto =
                paymentService.findAllByUser().stream().map(Payment::toDto).toList();

        return ResponseEntity.ok(paymentsDto);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable @Min(1) long paymentId) {
        Payment paymentById = paymentService.findById(paymentId);

        return ResponseEntity.ok(paymentById.toDto());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@Valid @RequestBody PaymentDto paymentDto) {
        Payment payment = paymentDto.toEntity();

        Payment savedPayment = paymentService.save(payment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment.toDto());
    }

    @PostMapping("/execute")
    public ResponseEntity<PaymentDto> executePayment(
            @Min(1) @RequestParam(value = "paymentId") long paymentId,
            @RequestParam(value = "sum") @Min(1) double sum) {
        log.info("Received request to execute a payment with id = {}", paymentId);

        Payment payment = paymentProcessorFacade.executePayment(paymentId, sum);

        return ResponseEntity.ok(payment.toDto());
    }
}
