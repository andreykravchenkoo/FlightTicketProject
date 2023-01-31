package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.dto.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.service.FlightService;;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
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

    private final GoflightlabsClientService flightsApiService;

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flightsDTO = flightService.findAll().stream()
                .map(EntityDTOMapper::mapFlightToFlightDTO)
                .toList();

        return new ResponseEntity<>(flightsDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Set<FlightDTO>> getFlightsByInfo(@RequestParam(value = "adults") String adults,
                                                           @RequestParam(value = "origin") String origin,
                                                           @RequestParam(value = "destination") String destination,
                                                           @RequestParam(value = "departureDate") String departureDate,
                                                           @RequestParam(value = "fareClass") String fareClass) {
        Set<ExternalApiFlightResponse.Item> responseFlights = flightsApiService.findFlightsByInfo(adults, origin, destination, departureDate, fareClass);

        Set<FlightDTO> flightsDTO = responseFlights.stream()
                .map(EntityDTOMapper::mapExternalApiFlightResponseToFLightDTO)
                .collect(Collectors.toSet());

        flightService.saveAll(flightsDTO.stream()
                .map(EntityDTOMapper::mapFlightDTOToEntity)
                .collect(Collectors.toSet()));

        return new ResponseEntity<>(flightsDTO, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable String flightId) {
        Flight flightById = flightService.findById(flightId);

        return new ResponseEntity<>(EntityDTOMapper.mapFlightToFlightDTO(flightById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightDTO> saveFlight(@RequestBody FlightDTO flightDTO) {
        Flight flight = EntityDTOMapper.mapFlightDTOToEntity(flightDTO);

        Flight savedFlight = flightService.save(flight);

        return new ResponseEntity<>(EntityDTOMapper.mapFlightToFlightDTO(savedFlight), HttpStatus.CREATED);
    }
}
