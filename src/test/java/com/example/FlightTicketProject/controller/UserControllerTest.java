package com.example.FlightTicketProject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.FlightTicketProject.dto.UserDto;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserService userService;

    @Test
    void testShouldFindAllUsers() throws Exception {
        List<UserDto> expectedUsersDto =
                List.of(new UserDto(1L, "Test", "Test", "test@gmail.com", "test", UserRole.USER));

        List<User> expectedUsers = List.of(expectedUsersDto.get(0).toEntity());

        when(userService.findAll()).thenReturn(expectedUsers);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<UserDto> users =
                objectMapper.readValue(responseBody, new TypeReference<List<UserDto>>() {});

        assertEquals(expectedUsersDto, users);
    }

    @Test
    void testShouldFindUserById() throws Exception {
        long expectedId = 1L;

        UserDto expectedUsersDto = new UserDto();
        expectedUsersDto.setId(expectedId);

        User expectedUser = expectedUsersDto.toEntity();

        when(userService.findById(expectedId)).thenReturn(expectedUser);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{userId}", expectedId))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseBody, new TypeReference<UserDto>() {});

        assertEquals(expectedUsersDto, userDto);
    }

    @Test
    void testShouldFindUserByEmail() throws Exception {
        UserDto expectedUsersDto = new UserDto();
        expectedUsersDto.setEmail("test@gmail.com");

        User expectedUser = expectedUsersDto.toEntity();

        when(userService.findByEmail()).thenReturn(expectedUser);

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/users/info"))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseBody, new TypeReference<UserDto>() {});

        assertEquals(expectedUsersDto, userDto);
    }

    @Test
    void testShouldSaveUser() throws Exception {
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(1L);
        expectedUserDto.setFirstname("Test");
        expectedUserDto.setLastname("Test");
        expectedUserDto.setEmail("test@gmail.com");
        expectedUserDto.setPassword("test");
        expectedUserDto.setRole(UserRole.USER);

        User expectedUser = expectedUserDto.toEntity();

        String requestBody = objectMapper.writeValueAsString(expectedUserDto);

        when(userService.save(any(User.class))).thenReturn(expectedUser);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/users")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isCreated())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(responseBody, new TypeReference<UserDto>() {});

        assertEquals(expectedUserDto, userDto);
    }
}
