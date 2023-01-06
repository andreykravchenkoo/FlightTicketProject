package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.entity.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> findAll();

    Flight findById(Long flightId);

    Flight save(Flight flight);

    void update(Flight flight);
}
