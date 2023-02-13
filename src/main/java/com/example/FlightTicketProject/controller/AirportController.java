package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDTO;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = "Test airport controller")
@RequestMapping("/api/airports")
@RestController
@Slf4j
public class AirportController {

    private final GoflightlabsClientService goflightlabsClientService;

    @GetMapping("/search")
    public ResponseEntity<List<AirportInfoDTO>> getAirportByCity(@RequestParam @NotBlank String city) {
        log.info("Received request to get airport by city = {}", city);

        List<AirportInfoDTO> airportInfoDTO = goflightlabsClientService.findAirportByCity(city).stream()
                .map(EntityDTOMapper::mapExternalApiAirportResponseToAirportInfoDTO)
                .toList();

        log.info("Request completed successfully IATA code = {}", airportInfoDTO.get(0));
        return new ResponseEntity<>(airportInfoDTO, HttpStatus.OK);
    }
}
