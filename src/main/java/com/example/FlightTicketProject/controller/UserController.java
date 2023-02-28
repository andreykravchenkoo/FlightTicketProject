package com.example.FlightTicketProject.controller;

import com.example.FlightTicketProject.dto.UserDto;
import com.example.FlightTicketProject.entity.User;
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
@Api("User controller")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = userService.findAll().stream()
                .map(User::toDto)
                .toList();

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable @Min(1) long userId) {
        User userById = userService.findById(userId);

        return ResponseEntity.ok(userById.toDto());
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserByEmail() {
        User user = userService.findByEmail();

        return ResponseEntity.ok(user.toDto());
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) {
        User user = userDto.toEntity();

        User savedUser = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.toDto());
    }
}
