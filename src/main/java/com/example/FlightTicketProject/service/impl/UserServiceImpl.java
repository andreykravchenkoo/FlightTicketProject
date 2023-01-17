package com.example.FlightTicketProject.service.impl;

import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.exception.EmailAlreadyTakenException;
import com.example.FlightTicketProject.exception.EmailNotValidException;
import com.example.FlightTicketProject.exception.UserNotFoundException;
import com.example.FlightTicketProject.repository.UserRepository;
import com.example.FlightTicketProject.service.UserService;
import com.example.FlightTicketProject.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private EmailValidator emailValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with ID = " + userId + " will not be found"));
    }

    @Override
    public User save(User user) {
        if (!emailValidator.isEmailValid(user.getEmail())) {
            throw new EmailNotValidException("Email = " + user.getEmail() + " not valid");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException("Email = " + user.getEmail() + " already taken");
        }

        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
