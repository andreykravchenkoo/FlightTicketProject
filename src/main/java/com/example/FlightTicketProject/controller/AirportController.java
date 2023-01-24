package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDTO;
import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.service.rest.FlightsApiService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api(tags = "Test airport controller")
@RequestMapping("/api/airports")
@RestController
public class AirportController {

    private final FlightsApiService flightsApiService;

    @GetMapping("/search")
    public List<AirportInfoDTO> getAirportByCity(@RequestParam String city) {
        List<ExternalApiAirportResponse.Data> airports = flightsApiService.findAirportByCity(city);

        return airports.stream()
                .map(AirportInfoDTO::new)
                .toList();
    }
}
