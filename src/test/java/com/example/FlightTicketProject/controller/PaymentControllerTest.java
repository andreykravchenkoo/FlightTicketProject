package com.example.FlightTicketProject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.FlightTicketProject.dto.PaymentDto;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.PaymentStatus;
import com.example.FlightTicketProject.facade.PaymentProcessorFacade;
import com.example.FlightTicketProject.service.PaymentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class PaymentControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private PaymentService paymentService;

    @MockBean private PaymentProcessorFacade paymentProcessorFacade;

    @Test
    void testShouldFindAllPayments() throws Exception {
        List<PaymentDto> expectedPaymentsDto =
                List.of(new PaymentDto(1L, "Test Test", 100, new Date(), PaymentStatus.NEW));

        List<Payment> expectedPayments = List.of(expectedPaymentsDto.get(0).toEntity());

        when(paymentService.findAll()).thenReturn(expectedPayments);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/payments"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<PaymentDto> payments =
                objectMapper.readValue(responseBody, new TypeReference<List<PaymentDto>>() {});

        assertEquals(expectedPaymentsDto, payments);
    }

    @Test
    void testShouldFindAllPaymentsByUser() throws Exception {
        List<PaymentDto> expectedPaymentsDto =
                List.of(new PaymentDto(1L, "Test Test", 100, new Date(), PaymentStatus.NEW));

        List<Payment> expectedPayments = List.of(expectedPaymentsDto.get(0).toEntity());

        when(paymentService.findAllByUser()).thenReturn(expectedPayments);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/user"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<PaymentDto> payments =
                objectMapper.readValue(responseBody, new TypeReference<List<PaymentDto>>() {});

        assertEquals(expectedPaymentsDto, payments);
    }

    @Test
    void testShouldFindPaymentById() throws Exception {
        long expectedId = 1L;

        PaymentDto expectedPaymentDto = new PaymentDto();
        expectedPaymentDto.setId(expectedId);

        Payment expectedPayment = expectedPaymentDto.toEntity();

        when(paymentService.findById(expectedId)).thenReturn(expectedPayment);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/payments/{paymentId}", expectedId))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaymentDto paymentDto =
                objectMapper.readValue(responseBody, new TypeReference<PaymentDto>() {});

        assertEquals(expectedPaymentDto, paymentDto);
    }

    @Test
    void testShouldSavePayment() throws Exception {
        PaymentDto expectedPaymentDto = new PaymentDto();
        expectedPaymentDto.setId(1L);
        expectedPaymentDto.setOwner("Test Test");
        expectedPaymentDto.setSum(100);
        expectedPaymentDto.setDate(new Date());
        expectedPaymentDto.setStatus(PaymentStatus.NEW);

        Payment expectedPayment = expectedPaymentDto.toEntity();

        String requestBody = objectMapper.writeValueAsString(expectedPaymentDto);

        when(paymentService.save(any(Payment.class))).thenReturn(expectedPayment);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/payments")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isCreated())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaymentDto paymentDto =
                objectMapper.readValue(responseBody, new TypeReference<PaymentDto>() {});

        assertEquals(expectedPaymentDto, paymentDto);
    }

    @Test
    void testShouldExecutePayment() throws Exception {
        long expectedPaymentId = 1L;
        double expectedSum = 100;

        PaymentDto expectedPaymentDto = new PaymentDto();
        expectedPaymentDto.setId(expectedPaymentId);
        expectedPaymentDto.setOwner("Test Test");
        expectedPaymentDto.setSum(expectedSum);
        expectedPaymentDto.setDate(new Date());
        expectedPaymentDto.setStatus(PaymentStatus.NEW);

        Payment expectedPayment = expectedPaymentDto.toEntity();

        when(paymentProcessorFacade.executePayment(expectedPaymentId, expectedSum))
                .thenReturn(expectedPayment);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/payments/execute")
                                        .param("paymentId", "1")
                                        .param("sum", "100"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaymentDto paymentDto =
                objectMapper.readValue(responseBody, new TypeReference<PaymentDto>() {});

        assertEquals(expectedPaymentDto, paymentDto);
    }
}
