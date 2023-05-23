package com.example.FlightTicketProject.repository;

import com.example.FlightTicketProject.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = ?", nativeQuery = true)
    Optional<User> findByEmail(String email);
}
