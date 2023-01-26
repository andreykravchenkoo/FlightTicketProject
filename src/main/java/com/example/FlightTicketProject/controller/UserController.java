package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.mapper.entity.EntityMapper;
import com.example.FlightTicketProject.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api("Test user controller")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    private final EntityMapper entityMapper;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.findAll();

        return users.stream()
                .map(UserDTO::new)
                .toList();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        User userById = userService.findById(userId);

        return new ResponseEntity<>(new UserDTO(userById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User user = entityMapper.mapUserDTOToEntity(userDTO);

        User savedUser = userService.save(user);

        return new ResponseEntity<>(new UserDTO(savedUser), HttpStatus.CREATED);
    }
}
