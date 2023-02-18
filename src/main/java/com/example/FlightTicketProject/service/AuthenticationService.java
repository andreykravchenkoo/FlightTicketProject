package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.dto.AuthenticationRequestDTO;
import com.example.FlightTicketProject.dto.AuthenticationResponseDTO;
import com.example.FlightTicketProject.dto.RegisterRequestDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
