package com.example.FlightTicketProject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.entity.FareClassStatus;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
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
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class FlightControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private FlightService flightService;

    @MockBean private GoflightlabsClientService goflightlabsClientService;

    @Test
    void testShouldFindAllFlight() throws Exception {
        List<FlightDto> expectedFlightsDto =
                List.of(
                        new FlightDto(
                                "1",
                                "London",
                                "Berlin",
                                new Date(),
                                new Date(),
                                100,
                                FareClassStatus.ECONOMY,
                                "Iberia Express"));

        List<Flight> expectedFlight = List.of(expectedFlightsDto.get(0).toEntity());

        when(flightService.findAll()).thenReturn(expectedFlight);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/flights"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<FlightDto> flights =
                objectMapper.readValue(responseBody, new TypeReference<List<FlightDto>>() {});

        assertEquals(expectedFlightsDto, flights);
    }

    @Test
    void testShouldFindFlightsByFilter() throws Exception {
        String expectedAdults = "1";
        String expectedOrigin = "LOND";
        String expectedDestination = "BER";
        String expectedDepartureDate = "2023-03-15";
        String expectedFareClass = "economy";

        Set<FlightDto> expectedFlightsDto =
                Set.of(
                        new FlightDto(
                                "1",
                                "London",
                                "Berlin",
                                new Date(),
                                new Date(),
                                100,
                                FareClassStatus.ECONOMY,
                                "Iberia Express"));

        when(goflightlabsClientService.findFlightsByFilter(
                        expectedAdults,
                        expectedOrigin,
                        expectedDestination,
                        expectedDepartureDate,
                        expectedFareClass))
                .thenReturn(expectedFlightsDto);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/flights/search")
                                        .param("adults", expectedAdults)
                                        .param("origin", expectedOrigin)
                                        .param("destination", expectedDestination)
                                        .param("departureDate", expectedDepartureDate)
                                        .param("fareClass", expectedFareClass))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Set<FlightDto> flights =
                objectMapper.readValue(responseBody, new TypeReference<Set<FlightDto>>() {});

        assertEquals(expectedFlightsDto, flights);
    }

    @Test
    void testShouldFindAllFlightByUser() throws Exception {
        List<FlightDto> expectedFlightsDto =
                List.of(
                        new FlightDto(
                                "1",
                                "London",
                                "Berlin",
                                new Date(),
                                new Date(),
                                100,
                                FareClassStatus.ECONOMY,
                                "Iberia Express"));

        List<Flight> expectedFlight = List.of(expectedFlightsDto.get(0).toEntity());

        when(flightService.findAllByUser()).thenReturn(expectedFlight);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/user"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<FlightDto> flights =
                objectMapper.readValue(responseBody, new TypeReference<List<FlightDto>>() {});

        assertEquals(expectedFlightsDto, flights);
    }

    @Test
    void testShouldFindFlightById() throws Exception {
        String expectedId = "1";

        FlightDto expectedFlightDto = new FlightDto();
        expectedFlightDto.setId(expectedId);

        Flight expectedFlight = expectedFlightDto.toEntity();

        when(flightService.findById(expectedId)).thenReturn(expectedFlight);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/{flightId}", expectedId))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        FlightDto flightDto =
                objectMapper.readValue(responseBody, new TypeReference<FlightDto>() {});

        assertEquals(expectedFlightDto, flightDto);
    }

    @Test
    void testShouldSaveFlight() throws Exception {
        FlightDto expectedFlightDto = new FlightDto();
        expectedFlightDto.setId("1");
        expectedFlightDto.setOrigin("London");
        expectedFlightDto.setDestination("Berlin");
        expectedFlightDto.setDeparture(new Date());
        expectedFlightDto.setArrival(new Date());
        expectedFlightDto.setPrice(100);
        expectedFlightDto.setFareClass(FareClassStatus.ECONOMY);
        expectedFlightDto.setCarrier("Iberia Express");

        Flight expectedFlight = expectedFlightDto.toEntity();

        String requestBody = objectMapper.writeValueAsString(expectedFlightDto);

        when(flightService.save(any(Flight.class))).thenReturn(expectedFlight);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/flights")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isCreated())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        FlightDto flightDto =
                objectMapper.readValue(responseBody, new TypeReference<FlightDto>() {});

        assertEquals(expectedFlightDto, flightDto);
    }
}
