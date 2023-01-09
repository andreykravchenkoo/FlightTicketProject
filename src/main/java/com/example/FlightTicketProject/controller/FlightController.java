package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.service.FlightService;;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Test flight controller")
@RequestMapping("/api/flights")
@RestController
public class FlightController {

    private FlightService flightService;


    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightService.findAll();

        return flights.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{flightId}")
    public FlightDTO getFlightById(@PathVariable long flightId) {
        Flight flightById = flightService.findById(flightId);

        return convertToDTO(flightById);
    }

    @PostMapping("")
    public FlightDTO saveFlight(@RequestBody FlightDTO flightDTO) {
        Flight flight = convertToEntity(flightDTO);

        Flight savedFlight = flightService.save(flight);

        return convertToDTO(savedFlight);
    }

    private Flight convertToEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();

        flight.setId(flightDTO.getId());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setDeparture(flightDTO.getDeparture());
        flight.setArrival(flightDTO.getArrival());
        flight.setPrice(flightDTO.getPrice());

        return flight;
    }

    private FlightDTO convertToDTO(Flight flight) {
        return new FlightDTO(flight);
    }
}
