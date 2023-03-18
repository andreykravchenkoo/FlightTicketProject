package com.example.FlightTicketProject.security.service.impl;

import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.response.AuthenticationResponseDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.exception.ResourceNotFound;
import com.example.FlightTicketProject.repository.UserRepository;
import com.example.FlightTicketProject.security.service.token.JwtTokenService;
import com.example.FlightTicketProject.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto register(RegisterRequestDto requestDTO) {
        log.info("Registering a new user with role 'User");

        User user = new User(
                requestDTO.getFirstname(),
                requestDTO.getLastname(),
                requestDTO.getEmail(),
                passwordEncoder.encode(requestDTO.getPassword()),
                UserRole.USER
        );

        userRepository.save(user);

        String jwtToken = jwtTokenService.generateToken(user);

        return new AuthenticationResponseDto(jwtToken);
    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        log.info("Registered user authentication");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFound("User by email = " + request.getEmail() + " not found"));

        String jwtToken = jwtTokenService.generateToken(user);

        return new AuthenticationResponseDto(jwtToken);
    }
}
