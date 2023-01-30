package com.example.FlightTicketProject.service.rest;

import com.example.FlightTicketProject.exception.DateNotValidException;
import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.validator.DateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlightsApiService {

    private final RestTemplate restTemplate;

    private final DateValidator dateValidator;

    @Value("${url.access-key}")
    private String accessKey;

    public Set<ExternalApiFlightResponse.Item> findFlightsByInfo(String adults, String origin, String destination, String departureDate, String fareClass) {
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + accessKey + "&adults={adults}&origin={origin}&destination={destination}&departureDate={departureDate}&cabinClass={fareClass}";

        if (!dateValidator.isDateValid(departureDate)) {
            throw new DateNotValidException("Date = " + departureDate + " is not valid");
        }

        Map<String, String> params = Map.of(
                "adults", adults,
                "origin", origin,
                "destination", destination,
                "departureDate", departureDate,
                "fareClass", fareClass.toLowerCase()
        );

        ExternalApiFlightResponse responseJson = restTemplate.getForObject(url, ExternalApiFlightResponse.class, params);

        return responseJson.getData()
                .getBuckets()
                .stream()
                .flatMap(x -> x.getItems().stream())
                .peek(x -> x.setFareClass(fareClass))
                .collect(Collectors.toSet());
    }

    public List<ExternalApiAirportResponse.Data> findAirportByCity(String city) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + accessKey + "&query={query}";

        Map<String, String> param = Map.of("query", city);

        ExternalApiAirportResponse responseJson = restTemplate.getForObject(url, ExternalApiAirportResponse.class, param);

        return responseJson.getData();
    }
}
