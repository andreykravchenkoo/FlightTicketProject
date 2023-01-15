package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.EntityMapper;
import com.example.FlightTicketProject.service.FlightService;;
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

    @Autowired
    public FlightController(FlightService flightService, EntityMapper entityMapper) {
        this.flightService = flightService;
        this.entityMapper = entityMapper;
    }

    @GetMapping("")
    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightService.findAll();

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
