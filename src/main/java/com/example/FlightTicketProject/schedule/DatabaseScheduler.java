package com.example.FlightTicketProject.schedule;

import com.example.FlightTicketProject.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@EnableScheduling
@Service
public class DatabaseScheduler {

    private final FlightRepository flightRepository;

    @Transactional
    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.MINUTES)
    public void deleteUnrelatedFlights() {
        flightRepository.deleteAllUnrelated();
    }
}
