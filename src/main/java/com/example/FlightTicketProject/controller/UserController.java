package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.mapper.EntityDTOMapper;
import com.example.FlightTicketProject.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequiredArgsConstructor
@Api("Test user controller")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userService.findAll().stream()
                .map(EntityDTOMapper::mapUserToUserDTO)
                .toList();

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable @Min(1) long userId) {
        User userById = userService.findById(userId);

        return new ResponseEntity<>(EntityDTOMapper.mapUserToUserDTO(userById), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) {
        User user = EntityDTOMapper.mapUserDTOToEntity(userDTO);

        User savedUser = userService.save(user);

        return new ResponseEntity<>(EntityDTOMapper.mapUserToUserDTO(savedUser), HttpStatus.CREATED);
    }
}
