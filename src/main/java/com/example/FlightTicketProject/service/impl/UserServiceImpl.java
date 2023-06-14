package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.exception.ResourceNotFoundException;
import com.example.FlightTicketProject.repository.UserRepository;
import com.example.FlightTicketProject.security.configuration.JwtAuthenticationFilter;
import com.example.FlightTicketProject.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID = " + userId + " not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail() {
        return userRepository
                .findByEmail(JwtAuthenticationFilter.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
