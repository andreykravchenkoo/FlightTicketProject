package com.example.FlightTicketProject.service.rest;

import com.example.FlightTicketProject.deserializer.GoflightlabsResponseCustomDeserializer;
import com.example.FlightTicketProject.dto.AirportInfoDto;
import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.entity.FareClassStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class GoflightlabsClientService {

    private final RestTemplate restTemplate;

    private final GoflightlabsResponseCustomDeserializer customDeserializer;

    @Value("${url.base}")
    private String baseUrl;

    @Value("${url.access-key}")
    private String accessKey;

    public Set<FlightDto> findFlightsByFilter(String adults, String origin, String destination, String departureDate, String fareClass) throws JsonProcessingException {
        log.info("Finding flights in Goflightlabs by filter: adults = {}, origin = {}, destination = {}, departureDate = {}, fareClass = {}", adults, origin, destination, departureDate, fareClass);

        String path = "/search-best-flights?access_key={accessKey}&adults={adults}&origin={origin}&destination={destination}&departureDate={departureDate}&cabinClass={fareClass}";

        Map<String, String> params = Map.of(
                "accessKey", accessKey,
                "adults", adults,
                "origin", origin,
                "destination", destination,
                "departureDate", departureDate,
                "fareClass", fareClass.toLowerCase()
        );

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path).buildAndExpand(params).toUriString();

        String json = restTemplate.getForObject(url, String.class);

        log.info("Flights in Goflightlabs found successfully");
        return customDeserializer.flightResponseDeserialize(json).stream()
                .peek(status -> status.setFareClass(FareClassStatus.valueOf(fareClass.toUpperCase())))
                .collect(Collectors.toSet());
    }

    public List<AirportInfoDto> findAirportByCity(String city) throws JsonProcessingException {
        log.info("Finding airport data in Goflightlabs by city = {}", city);

        String path = "/get-airport-data?access_key={accessKey}&query={query}";

        Map<String, String> param = Map.of(
                "accessKey", accessKey,
                "query", city
        );

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path).buildAndExpand(param).toUriString();

        String json = restTemplate.getForObject(url, String.class);

        log.info("Airport data found successfully");
        return customDeserializer.airportResponseDeserialize(json);
    }
}
