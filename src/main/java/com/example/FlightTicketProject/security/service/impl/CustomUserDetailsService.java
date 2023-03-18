package com.example.FlightTicketProject.security.service.impl;

import com.example.FlightTicketProject.exception.ResourceNotFound;
import com.example.FlightTicketProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User by email = " + email + " not found"));
    }
}
