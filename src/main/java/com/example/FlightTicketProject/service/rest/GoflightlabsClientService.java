package com.example.FlightTicketProject.service.rest;

import com.example.FlightTicketProject.exception.DateNotValidException;
import com.example.FlightTicketProject.dto.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.dto.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.validator.DateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GoflightlabsClientService {

    private final RestTemplate restTemplate;

    private final DateValidator dateValidator;

    @Value("${url.base}")
    private String baseUrl;

    @Value("${url.access-key}")
    private String accessKey;

    public Set<ExternalApiFlightResponse.Item> findFlightsByInfo(String adults, String origin, String destination, String departureDate, String fareClass) {
        String path = "/search-best-flights?access_key={accessKey}&adults={adults}&origin={origin}&destination={destination}&departureDate={departureDate}&cabinClass={fareClass}";

        if (!dateValidator.isDateValid(departureDate)) {
            throw new DateNotValidException("Date = " + departureDate + " is not valid");
        }

        Map<String, String> params = Map.of(
                "accessKey", accessKey,
                "adults", adults,
                "origin", origin,
                "destination", destination,
                "departureDate", departureDate,
                "fareClass", fareClass.toLowerCase()
        );

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path).buildAndExpand(params).toUriString();

        ExternalApiFlightResponse responseJson = restTemplate.getForObject(url, ExternalApiFlightResponse.class);

        return Optional.ofNullable(responseJson)
                .map(ExternalApiFlightResponse::getData)
                .map(ExternalApiFlightResponse.Data::getBuckets)
                .map(buckets -> buckets.stream()
                        .flatMap(bucket -> bucket.getItems().stream())
                        .peek(item -> item.setFareClass(fareClass))
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    public List<ExternalApiAirportResponse.Data> findAirportByCity(String city) {
        String path = "/get-airport-data?access_key={accessKey}&query={query}";

        Map<String, String> param = Map.of(
                "accessKey", accessKey,
                "query", city
        );

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl).path(path).buildAndExpand(param).toUriString();

        ExternalApiAirportResponse responseJson = restTemplate.getForObject(url, ExternalApiAirportResponse.class);

        return Optional.ofNullable(responseJson)
                .map(ExternalApiAirportResponse::getData)
                .orElse(List.of());
    }
}
