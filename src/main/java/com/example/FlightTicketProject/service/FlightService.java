package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.entity.Flight;

import java.util.List;
import java.util.Set;

public interface FlightService {

    List<Flight> findAll();

    Flight findById(String flightId);

    Flight save(Flight flight);

    void cacheAll(Set<Flight> flights);

    void update(Flight flight);

    double findPriceFlightByPaymentId(long paymentId);

    List<Flight> findAllByUser();
}
