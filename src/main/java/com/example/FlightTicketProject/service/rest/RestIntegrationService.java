package com.example.FlightTicketProject.service.rest;

import com.example.FlightTicketProject.exception.DateNotValidException;
import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RestIntegrationService {

    private RestTemplate restTemplate;

    private DateValidator dateValidator;

    @Value("${url.access-key}")
    private String accessKey;

    @Autowired
    public RestIntegrationService(RestTemplate restTemplate, DateValidator dateValidator) {
        this.restTemplate = restTemplate;
        this.dateValidator = dateValidator;
    }

    public List<ExternalApiFlightResponse.Item> findFlightsByInfo(String adults, String origin, String destination, String departureDate) {
        String url = "https://app.goflightlabs.com/search-best-flights?access_key=" + accessKey + "&adults={adults}&origin={origin}&destination={destination}&departureDate={departureDate}";

        if (!dateValidator.isDateValid(departureDate)) {
            throw new DateNotValidException("Date = " + departureDate + " is not valid");
        }

        Map<String, String> params = new HashMap<>();
        params.put("adults", adults);
        params.put("origin", origin);
        params.put("destination", destination);
        params.put("departureDate", departureDate);

        ExternalApiFlightResponse responseJson = restTemplate.getForObject(url, ExternalApiFlightResponse.class, params);

        return responseJson.getData().getBuckets().stream().flatMap(x -> x.getItems().stream()).toList();
    }

    public List<ExternalApiAirportResponse.Data> findAirportByCity(String city) {
        String url = "https://app.goflightlabs.com/get-airport-data?access_key=" + accessKey + "&query={query}";

        Map<String, String> param = new HashMap<>();
        param.put("query", city);

        ExternalApiAirportResponse responseJson = restTemplate.getForObject(url, ExternalApiAirportResponse.class, param);

        return responseJson.getData();
    }
}
