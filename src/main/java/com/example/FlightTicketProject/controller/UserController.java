package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.UserDTO;
import com.example.FlightTicketProject.entity.User;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

        return new ResponseEntity<>(convertToDTO(userById), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);

        User savedUser = userService.save(user);

        return new ResponseEntity<>(convertToDTO(savedUser), HttpStatus.CREATED);
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user);
    }
}
