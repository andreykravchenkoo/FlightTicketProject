package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.service.FlightService;;
import com.example.FlightTicketProject.service.rest.FlightsApiService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Api(tags = "Test flight controller")
@RequestMapping("/api/flights")
@RestController
public class FlightController {

    private final FlightService flightService;

    private final FlightsApiService flightsApiService;

    private final EntityMapper entityMapper;

    @GetMapping("/all")
    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightService.findAll();

        return flights.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public Set<FlightDTO> getFlightsByInfo(@RequestParam(value = "adults") String adults,
                                           @RequestParam(value = "origin") String origin,
                                           @RequestParam(value = "destination") String destination,
                                           @RequestParam(value = "departureDate") String departureDate,
                                           @RequestParam(value = "fareClass") String fareClass) {
        Set<ExternalApiFlightResponse.Item> flights = flightsApiService.findFlightsByInfo(adults, origin, destination, departureDate, fareClass);

        Set<FlightDTO> flightsDTO = flights.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toSet());

        flightService.saveAll(flightsDTO.stream()
                .map(entityMapper::mapFlightDTOToEntity)
                .collect(Collectors.toSet()));

        return flightsDTO;
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String flightId) {
        Flight flightById = flightService.findById(flightId);

        return new ResponseEntity<>(new FlightDTO(flightById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightDTO> saveFlight(@RequestBody FlightDTO flightDTO) {
        Flight flight = entityMapper.mapFlightDTOToEntity(flightDTO);

        Flight savedFlight = flightService.save(flight);

        return new ResponseEntity<>(new FlightDTO(savedFlight), HttpStatus.CREATED);
    }
}
