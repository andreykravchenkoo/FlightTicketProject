package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.response.AuthenticationResponseDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.security.service.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "Authentication controller")
@RequestMapping("/api/authentication")
@RestController
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody @Valid RegisterRequestDto requestDto) {
        log.info("Received request to register new user");

        AuthenticationResponseDto responseDto = authenticationService.register(requestDto);

        log.info("Request completed successfully. New user registered");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        log.info("Received request to authenticate user");

        AuthenticationResponseDto responseDto = authenticationService.authenticate(requestDto);

        log.info("Request completed successfully. User authenticated");
        return ResponseEntity.ok(responseDto);
    }
}
