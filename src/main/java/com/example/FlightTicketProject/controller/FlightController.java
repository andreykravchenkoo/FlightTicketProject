package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.service.FlightService;;
import com.example.FlightTicketProject.service.rest.RestIntegrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Test flight controller")
@RequestMapping("/api/flights")
@RestController
public class FlightController {

    private FlightService flightService;

    private EntityMapper entityMapper;

    private RestIntegrationService restIntegrationService;

    @Autowired
    public FlightController(FlightService flightService, EntityMapper entityMapper, RestIntegrationService restIntegrationService) {
        this.flightService = flightService;
        this.entityMapper = entityMapper;
        this.restIntegrationService = restIntegrationService;
    }

    @GetMapping("")
    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightService.findAll();

        return flights.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{adults}/{origin}/{destination}/{departureDate}")
    public List<FlightDTO> getFlightsByInfo(@PathVariable String adults,
                                            @PathVariable String origin,
                                            @PathVariable String destination,
                                            @PathVariable String departureDate) throws JsonProcessingException {
        List<ExternalApiFlightResponse.Item> flights = restIntegrationService.findFlightsByInfo(adults, origin, destination, departureDate);

        return flights.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable long flightId) {
        Flight flightById = flightService.findById(flightId);

        return new ResponseEntity<>(new FlightDTO(flightById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<FlightDTO> saveFlight(@RequestBody FlightDTO flightDTO) {
        Flight flight = entityMapper.mapFlightDTOToEntity(flightDTO);

        Flight savedFlight = flightService.save(flight);

        return new ResponseEntity<>(new FlightDTO(savedFlight), HttpStatus.CREATED);
    }
}
