package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Api(tags = "Flight controller")
@RequestMapping("/api/flights")
@RestController
@Slf4j
public class FlightController {

    private final FlightService flightService;

    private final GoflightlabsClientService goflightlabsClientService;

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flightsDTO = flightService.findAll().stream()
                .map(Flight::toDto)
                .toList();

        return ResponseEntity.ok(flightsDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Set<FlightDto>> getFlightsByFilter(@RequestParam(value = "adults") @NotBlank String adults,
                                                             @RequestParam(value = "origin") @NotBlank String origin,
                                                             @RequestParam(value = "destination") @NotBlank String destination,
                                                             @RequestParam(value = "departureDate") @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$") String departureDate,
                                                             @RequestParam(value = "fareClass") @NotBlank String fareClass) throws JsonProcessingException {
        log.info("Received request to get flights by filter: adults = {}, origin = {}, destination = {}, departureDate = {}, fareClass = {}", adults, origin, destination, departureDate, fareClass);

        Set<FlightDto> flightsDto = goflightlabsClientService.findFlightsByFilter(adults, origin, destination, departureDate, fareClass);

        flightService.saveAll(flightsDto.stream()
                .map(FlightDto::toEntity)
                .collect(Collectors.toSet()));

        log.info("Request get flights by filter completed successfully");
        return ResponseEntity.ok(flightsDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<FlightDto>> getAllFlightByUser() {
        List<FlightDto> flightsDTO = flightService.findAllByUser().stream()
                .map(Flight::toDto)
                .toList();

        return ResponseEntity.ok(flightsDTO);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable @NotBlank String flightId) {
        Flight flightById = flightService.findById(flightId);

        return ResponseEntity.ok(flightById.toDto());
    }

    @PostMapping
    public ResponseEntity<FlightDto> saveFlight(@RequestBody @Valid FlightDto flightDto) {
        Flight flight = flightDto.toEntity();

        Flight savedFlight = flightService.save(flight);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlight.toDto());
    }
}
