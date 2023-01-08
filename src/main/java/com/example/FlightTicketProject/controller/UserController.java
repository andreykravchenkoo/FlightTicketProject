package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Test user controller")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }
}
