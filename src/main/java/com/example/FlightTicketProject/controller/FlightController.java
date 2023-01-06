package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.service.FlightService;;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Test controller")
@RequestMapping("/api/flights")
@RestController
public class FlightController {

    private FlightService flightService;


    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/")
    public List<Flight> getAllFlights() {
        return flightService.findAll();
    }

    @PostMapping("/")
    public Flight saveFlight(@RequestBody Flight flight) {
        return flightService.save(flight);
    }
}
