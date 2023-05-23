package com.example.FlightTicketProject.security.service;

import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.dto.response.AuthenticationResponseDto;

public interface AuthenticationService {

    AuthenticationResponseDto register(RegisterRequestDto request);

    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
}
