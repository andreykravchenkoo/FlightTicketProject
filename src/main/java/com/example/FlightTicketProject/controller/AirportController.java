package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDto;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = "Airport controller")
@RequestMapping("/api/airports")
@RestController
@Slf4j
public class AirportController {

    private final GoflightlabsClientService goflightlabsClientService;

    @GetMapping("/search")
    public ResponseEntity<List<AirportInfoDto>> getAirportByCity(@RequestParam @NotBlank String city) throws JsonProcessingException {
        log.info("Received request to get airport by city = {}", city);

        List<AirportInfoDto> airportInfoDTO = goflightlabsClientService.findAirportByCity(city);

        log.info("Request airport by city completed successfully");
        return ResponseEntity.ok(airportInfoDTO);
    }
}
