package com.example.FlightTicketProject.security.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.security.service.token.JwtTokenService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class SecurityConfigurationIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private JwtTokenService jwtTokenService;

    @Test
    void testShouldGiveAccessToRegisterResourcesWithoutAuthorization() throws Exception {
        String expectedFirstname = "firstname";
        String expectedLastname = "lastname";
        String expectedEmail = "test@gmail.com";
        String expectedPassword = "test";

        RegisterRequestDto expectedRequestDto = new RegisterRequestDto();
        expectedRequestDto.setFirstname(expectedFirstname);
        expectedRequestDto.setLastname(expectedLastname);
        expectedRequestDto.setEmail(expectedEmail);
        expectedRequestDto.setPassword(expectedPassword);

        User expectedUser = new User();
        expectedUser.setFirstname(expectedFirstname);
        expectedUser.setLastname(expectedLastname);
        expectedUser.setEmail(expectedEmail);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setRole(UserRole.USER);

        String requestBody = objectMapper.writeValueAsString(expectedRequestDto);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/authentication/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String jwt = objectMapper.readTree(responseBody).get("token").asText();

        assertTrue(jwtTokenService.isTokenValid(jwt, expectedUser));
    }

    @Test
    void testShouldGiveAccessToAuthenticateResourcesWithoutAuthorization() throws Exception {
        String expectedUserEmail = "admin@gmail.com";
        String expectedPassword = "admin";

        AuthenticationRequestDto expectedRequestDto = new AuthenticationRequestDto();
        expectedRequestDto.setEmail(expectedUserEmail);
        expectedRequestDto.setPassword(expectedPassword);

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setRole(UserRole.ADMIN);

        String requestBody = objectMapper.writeValueAsString(expectedRequestDto);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/authentication/authenticate")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String jwt = objectMapper.readTree(responseBody).get("token").asText();

        assertTrue(jwtTokenService.isTokenValid(jwt, expectedUser));
    }

    @Test
    void testShouldGiveAccessToAuthenticateResourcesWithoutAuthorizationButFail() throws Exception {
        String expectedUserEmail = "fail@gmail.com";
        String expectedPassword = "fail";
        String expectedErrorMessage = "User by email = " + expectedUserEmail + " not found";

        AuthenticationRequestDto expectedRequestDto = new AuthenticationRequestDto();
        expectedRequestDto.setEmail(expectedUserEmail);
        expectedRequestDto.setPassword(expectedPassword);

        String requestBody = objectMapper.writeValueAsString(expectedRequestDto);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/api/authentication/authenticate")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(requestBody))
                        .andExpect(status().isUnauthorized())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }

    @Test
    void testShouldGiveAccessToResourcesWithRoleUser() throws Exception {
        String expectedUserEmail = "admin@gmail.com";
        String expectedPassword = "admin";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setRole(UserRole.ADMIN);

        String expectedToken = jwtTokenService.generateToken(expectedUser);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/flights/user")
                                        .header("Authorization", "Bearer " + expectedToken))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Set<FlightDto> flights =
                objectMapper.readValue(responseBody, new TypeReference<Set<FlightDto>>() {});

        assertNotNull(flights);
    }

    @Test
    void testShouldCloseAccessToResourceWithUserRole() throws Exception {
        String expectedUserEmail = "user@gmail.com";
        String expectedPassword = "user";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setRole(UserRole.USER);

        String expectedToken = jwtTokenService.generateToken(expectedUser);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/flights")
                                .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGiveAccessToResourcesWithRoleAdmin() throws Exception {
        String expectedUserEmail = "admin@gmail.com";
        String expectedPassword = "admin";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setPassword(expectedPassword);
        expectedUser.setRole(UserRole.ADMIN);

        String expectedToken = jwtTokenService.generateToken(expectedUser);

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/flights")
                                        .header("Authorization", "Bearer " + expectedToken))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Set<FlightDto> flights =
                objectMapper.readValue(responseBody, new TypeReference<Set<FlightDto>>() {});

        assertNotNull(flights);
    }

    @Test
    void testCloseAccessToResourcesWithInvalidToken() throws Exception {
        String expectedToken = "invalidToken";
        String expectedErrorMessage =
                "JWT strings must contain exactly 2 period characters. Found: 0";

        MvcResult result =
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/api/flights")
                                        .header("Authorization", "Bearer " + expectedToken))
                        .andExpect(status().isUnauthorized())
                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }
}
