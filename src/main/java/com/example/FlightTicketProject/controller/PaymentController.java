package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.PaymentDTO;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.facade.PaymentProcessorFacade;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.service.PaymentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@Api("Test payment controller")
@RequestMapping("/api/payments")
@RestController
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentProcessorFacade paymentProcessorFacade;

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> paymentsDTO = paymentService.findAll().stream()
                .map(EntityDTOMapper::mapPaymentToPaymentDTO)
                .toList();

        return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<PaymentDTO>> getAllPaymentsByUser() {
        List<PaymentDTO> paymentsDTO = paymentService.findAllByUser().stream()
                .map(EntityDTOMapper::mapPaymentToPaymentDTO)
                .toList();

        return new ResponseEntity<>(paymentsDTO, HttpStatus.OK);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable @Min(1) long paymentId) {
        Payment paymentById = paymentService.findById(paymentId);

        return new ResponseEntity<>(EntityDTOMapper.mapPaymentToPaymentDTO(paymentById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> savePayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        Payment payment = EntityDTOMapper.mapPaymentDTOToEntity(paymentDTO);

        Payment savedPayment = paymentService.save(payment);

        return new ResponseEntity<>(EntityDTOMapper.mapPaymentToPaymentDTO(savedPayment), HttpStatus.CREATED);
    }

    @PostMapping("/execute")
    public ResponseEntity<PaymentDTO> executePayment(@Min(1) @RequestParam(value = "paymentId") long paymentId,
                                                     @RequestParam(value = "sum") @Min(1) double sum) {
        log.info("Received request to execute a payment with id = {}", paymentId);

        Payment payment = paymentProcessorFacade.executePayment(paymentId, sum);

        log.info("Payment executed successful with id = {}", payment.getId());
        return new ResponseEntity<>(EntityDTOMapper.mapPaymentToPaymentDTO(payment), HttpStatus.OK);
    }
}
