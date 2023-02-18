package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.AuthenticationRequestDTO;
import com.example.FlightTicketProject.dto.AuthenticationResponseDTO;
import com.example.FlightTicketProject.dto.RegisterRequestDTO;
import com.example.FlightTicketProject.service.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "Test authentication controller")
@RequestMapping("/api/authentication")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody @Valid RegisterRequestDTO requestDTO) {
        return new ResponseEntity<>(authenticationService.register(requestDTO), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody @Valid AuthenticationRequestDTO requestDTO) {
        return new ResponseEntity<>(authenticationService.authenticate(requestDTO), HttpStatus.OK);
    }
}
