package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.TicketDto;
import com.example.FlightTicketProject.entity.Ticket;
import com.example.FlightTicketProject.service.TicketService;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;

    @Test
    void testShouldFindAllTickets() throws Exception {
        List<TicketDto> expectedTicketsDto = List.of(
                new TicketDto(1L, "Test Test", "F10")
        );

        List<Ticket> expectedTickets = List.of(
                expectedTicketsDto.get(0).toEntity()
        );

        when(ticketService.findAll()).thenReturn(expectedTickets);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<TicketDto> tickets = objectMapper.readValue(responseBody, new TypeReference<List<TicketDto>>(){});

        assertEquals(expectedTicketsDto, tickets);
    }

    @Test
    void testShouldFindAllTicketsByUser() throws Exception {
        List<TicketDto> expectedTicketsDto = List.of(
                new TicketDto(1L, "Test Test", "F10")
        );

        List<Ticket> expectedTickets = List.of(
                expectedTicketsDto.get(0).toEntity()
        );

        when(ticketService.findAllByUser()).thenReturn(expectedTickets);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets/user"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<TicketDto> tickets = objectMapper.readValue(responseBody, new TypeReference<List<TicketDto>>(){});

        assertEquals(expectedTicketsDto, tickets);
    }

    @Test
    void testShouldFindTicketById() throws Exception {
        long expectedId = 1L;

        TicketDto expectedTicketDto = new TicketDto();
        expectedTicketDto.setId(expectedId);

        Ticket expectedTicket = expectedTicketDto.toEntity();

        when(ticketService.findById(expectedId)).thenReturn(expectedTicket);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets/{ticketId}", expectedId))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TicketDto ticketDto = objectMapper.readValue(responseBody, new TypeReference<TicketDto>(){});

        assertEquals(expectedTicketDto, ticketDto);
    }

    @Test
    void testShouldSaveTicket() throws Exception {
        TicketDto expectedTicketDto = new TicketDto();
        expectedTicketDto.setId(1L);
        expectedTicketDto.setOwner("Test Test");
        expectedTicketDto.setSeat("F10");

        Ticket expectedTicket= expectedTicketDto.toEntity();

        String requestBody = objectMapper.writeValueAsString(expectedTicketDto);

        when(ticketService.save(any(Ticket.class))).thenReturn(expectedTicket);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        TicketDto ticketDto = objectMapper.readValue(responseBody, new TypeReference<TicketDto>(){});

        assertEquals(expectedTicketDto, ticketDto);
    }
}