package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.PaymentDTO;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.facade.PaymentProcessorFacade;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
import com.example.FlightTicketProject.service.PaymentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api("Test payment controller")
@RequestMapping("/api/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentProcessorFacade paymentProcessorFacade;

    private final EntityMapper entityMapper;

    @GetMapping("/all")
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentService.findAll();

        return payments.stream()
                .map(PaymentDTO::new)
                .toList();
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable long paymentId) {
        Payment paymentById = paymentService.findById(paymentId);

        return new ResponseEntity<>(new PaymentDTO(paymentById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> savePayment(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = entityMapper.mapPaymentDTOToEntity(paymentDTO);

        Payment savedPayment = paymentService.save(payment);

        return new ResponseEntity<>(new PaymentDTO(savedPayment), HttpStatus.CREATED);
    }

    @PostMapping("/execute")
    public ResponseEntity<PaymentDTO> executePayment(@RequestParam(value = "paymentId") long paymentId,
                                                     @RequestParam(value = "sum") double sum) {
        Payment payment = paymentProcessorFacade.executePayment(paymentId, sum);

        return new ResponseEntity<>(new PaymentDTO(payment), HttpStatus.OK);
    }
}
