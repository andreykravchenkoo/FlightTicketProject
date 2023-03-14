package com.example.FlightTicketProject.service.rest;

import com.example.FlightTicketProject.dto.AirportInfoDto;
import com.example.FlightTicketProject.dto.FlightDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
@ActiveProfiles(profiles = "test")
class GoflightlabsClientServiceTest {

    private WireMockServer wireMockServer;

    private final String accessKey = "my-access-key";

    @Autowired
    private GoflightlabsClientService goflightlabsClientService;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9090);
        wireMockServer.start();
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testShouldFindFlightsByFilter() throws JsonProcessingException {
        String adults = "1";
        String origin = "LOND";
        String destination = "MAD";
        String departureDate = "2023-03-14";
        String fareClass = "economy";

        wireMockServer.stubFor(get(urlPathEqualTo("/search-best-flights"))
                .withQueryParam("access_key", equalTo(accessKey))
                .withQueryParam("adults", equalTo(adults))
                .withQueryParam("origin", equalTo(origin))
                .withQueryParam("destination", equalTo(destination))
                .withQueryParam("departureDate", equalTo(departureDate))
                .withQueryParam("cabinClass", equalTo(fareClass))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("flights.json")));

        Set<FlightDto> result = goflightlabsClientService.findFlightsByFilter(adults, origin, destination, departureDate, fareClass);

        assertNotNull(result);
    }

    @Test
    void testShouldFindAirportByCity() throws JsonProcessingException {
        String city = "berlin";

        wireMockServer.stubFor(get(urlPathEqualTo("/get-airport-data"))
                .withQueryParam("access_key", equalTo(accessKey))
                .withQueryParam("query", equalTo(city))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("airports.json")));

        List<AirportInfoDto> result = goflightlabsClientService.findAirportByCity(city);

        assertNotNull(result);
    }
}