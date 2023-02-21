package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.dto.AuthenticationRequestDTO;
import com.example.FlightTicketProject.dto.AuthenticationResponseDTO;
import com.example.FlightTicketProject.dto.RegisterRequestDTO;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.exception.EmailAlreadyTakenException;
import com.example.FlightTicketProject.exception.UserNotFoundException;
import com.example.FlightTicketProject.repository.UserRepository;
import com.example.FlightTicketProject.security.service.JwtTokenService;
import com.example.FlightTicketProject.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO requestDTO) {
        User user = new User(
                requestDTO.getFirstname(),
                requestDTO.getLastname(),
                requestDTO.getEmail(),
                passwordEncoder.encode(requestDTO.getPassword()),
                UserRole.USER
        );

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException("Email = " + user.getEmail() + " already taken");
        }

        userRepository.save(user);

        String jwtToken = jwtTokenService.generateToken(user);

        return new AuthenticationResponseDTO(jwtToken);
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User by email = " + request.getEmail() + " not found"));

        String jwtToken = jwtTokenService.generateToken(user);

        return new AuthenticationResponseDTO(jwtToken);
    }
}
