package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.exception.FlightNotFoundException;
import com.example.FlightTicketProject.repository.FlightRepository;
import com.example.FlightTicketProject.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight findById(Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(() -> new FlightNotFoundException());
    }

    @Override
    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public void update(Flight flight) {
        flightRepository.save(flight);
    }
}
