package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDTO;
import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.service.rest.RestIntegrationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Test airport controller")
@RequestMapping("/api/airports")
@RestController
public class AirportController {

    private RestIntegrationService restIntegrationService;

    @Autowired
    public AirportController(RestIntegrationService restIntegrationService) {
        this.restIntegrationService = restIntegrationService;
    }

    @GetMapping("/{city}")
    public List<AirportInfoDTO> getAirportByCity(@PathVariable String city) {
        List<ExternalApiAirportResponse.Data> airports = restIntegrationService.findAirportByCity(city);

        return airports.stream()
                .map(AirportInfoDTO::new)
                .collect(Collectors.toList());
    }
}
