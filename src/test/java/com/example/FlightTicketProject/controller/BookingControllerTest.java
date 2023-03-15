package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.BookTicketDto;
import com.example.FlightTicketProject.dto.TicketInfoWithPaymentIdDto;
import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.facade.BookingTicketFacade;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingTicketFacade bookingTicketFacade;

    @Test
    void testShouldBookTicket() throws Exception {
        String expectedFlightId = "1";
        String expectedSeat = "F10";

        TicketInfoWithPaymentIdDto expectedTicketInfoWithPaymentId = new TicketInfoWithPaymentIdDto();
        expectedTicketInfoWithPaymentId.setTicketId(1L);
        expectedTicketInfoWithPaymentId.setSeat(expectedSeat);
        expectedTicketInfoWithPaymentId.setPaymentId(1L);

        Payment expectedPayment = new Payment();
        expectedPayment.setId(1L);

        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(1L);
        expectedTicket.setSeat(expectedSeat);
        expectedTicket.setPayment(expectedPayment);

        BookTicketDto bookTicketDto = new BookTicketDto();
        bookTicketDto.setFlightId(expectedFlightId);
        bookTicketDto.setSeat(expectedSeat);

        String requestBody = objectMapper.writeValueAsString(bookTicketDto);

        when(bookingTicketFacade.bookTicket(expectedFlightId, expectedSeat)).thenReturn(expectedTicket);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TicketInfoWithPaymentIdDto ticketInfoWithPaymentIdDto = objectMapper.readValue(responseBody, new TypeReference<TicketInfoWithPaymentIdDto>(){});

        assertEquals(expectedTicketInfoWithPaymentId, ticketInfoWithPaymentIdDto);
    }
}