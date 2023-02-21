package com.example.FlightTicketProject.service;

import com.example.FlightTicketProject.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long userId);

    User save(User user);

    void update(User user);

    User findByEmail();
}
