package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.dto.response.ExternalApiFlightResponse;
import com.example.FlightTicketProject.service.FlightService;;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
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
@Api(tags = "Test flight controller")
@RequestMapping("/api/flights")
@RestController
@Slf4j
public class FlightController {

    private final FlightService flightService;

    private final GoflightlabsClientService goflightlabsClientService;

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flightsDTO = flightService.findAll().stream()
                .map(EntityDTOMapper::mapFlightToFlightDTO)
                .toList();

        return new ResponseEntity<>(flightsDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Set<FlightDTO>> getFlightsByFilter(@RequestParam(value = "adults") @NotBlank String adults,
                                                             @RequestParam(value = "origin") @NotBlank String origin,
                                                             @RequestParam(value = "destination") @NotBlank String destination,
                                                             @RequestParam(value = "departureDate") @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$") String departureDate,
                                                             @RequestParam(value = "fareClass") @NotBlank String fareClass) {
        log.info("Received request to get flights by filter: adults = {}, origin = {}, destination = {}, departureDate = {}, fareClass = {}", adults, origin, destination, departureDate, fareClass);

        Set<ExternalApiFlightResponse.Item> responseFlights = goflightlabsClientService.findFlightsByFilter(adults, origin, destination, departureDate, fareClass);

        Set<FlightDTO> flightsDTO = responseFlights.stream()
                .map(EntityDTOMapper::mapExternalApiFlightResponseToFLightDTO)
                .collect(Collectors.toSet());

        flightService.saveAll(flightsDTO.stream()
                .map(EntityDTOMapper::mapFlightDTOToEntity)
                .collect(Collectors.toSet()));

        log.info("Request get flights by filter completed successfully");
        return new ResponseEntity<>(flightsDTO, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<FlightDTO>> getAllFlightByUser() {
        List<FlightDTO> flightsDTO = flightService.findAllByUser().stream()
                .map(EntityDTOMapper::mapFlightToFlightDTO)
                .toList();

        return new ResponseEntity<>(flightsDTO, HttpStatus.OK);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable @NotBlank String flightId) {
        Flight flightById = flightService.findById(flightId);

        return new ResponseEntity<>(EntityDTOMapper.mapFlightToFlightDTO(flightById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightDTO> saveFlight(@RequestBody @Valid FlightDTO flightDTO) {
        Flight flight = EntityDTOMapper.mapFlightDTOToEntity(flightDTO);

        Flight savedFlight = flightService.save(flight);

        return new ResponseEntity<>(EntityDTOMapper.mapFlightToFlightDTO(savedFlight), HttpStatus.CREATED);
    }
}
