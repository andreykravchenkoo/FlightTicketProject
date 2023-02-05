package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AirportInfoDTO;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.service.rest.GoflightlabsClientService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = "Test airport controller")
@RequestMapping("/api/airports")
@RestController
public class AirportController {

    private final GoflightlabsClientService goflightlabsClientService;

    @GetMapping("/search")
    public ResponseEntity<List<AirportInfoDTO>> getAirportByCity(@RequestParam @NotBlank String city) {
        List<AirportInfoDTO> airportInfoDTO = goflightlabsClientService.findAirportByCity(city).stream()
                .map(EntityDTOMapper::mapExternalApiAirportResponseToAirportInfoDTO)
                .toList();

        return new ResponseEntity<>(airportInfoDTO, HttpStatus.OK);
    }
}
