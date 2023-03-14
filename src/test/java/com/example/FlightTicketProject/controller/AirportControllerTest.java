package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDto;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GoflightlabsClientService goflightlabsClientService;


    @Test
    void testShouldFindAirportsByCity() throws Exception {
        String expectedCity = "London";

        List<AirportInfoDto> expectedAirports = List.of(
                new AirportInfoDto("LOND", "London City", expectedCity, "UK")
        );

        when(goflightlabsClientService.findAirportByCity("London")).thenReturn(expectedAirports);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/airports/search")
                        .param("city", expectedCity))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<AirportInfoDto> airports = objectMapper.readValue(responseBody, new TypeReference<List<AirportInfoDto>>(){});

        assertEquals(expectedAirports, airports);
    }
}