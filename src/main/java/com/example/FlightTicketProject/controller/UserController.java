package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.mapper.EntityMapper;
import com.example.FlightTicketProject.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("Test user controller")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private UserService userService;

    private EntityMapper entityMapper;

    @Autowired
    public UserController(UserService userService, EntityMapper entityMapper) {
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        User userById = userService.findById(userId);

        return new ResponseEntity<>(new UserDTO(userById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User user = entityMapper.mapUserDTOToEntity(userDTO);

        User savedUser = userService.save(user);

        return new ResponseEntity<>(new UserDTO(savedUser), HttpStatus.CREATED);
    }
}
