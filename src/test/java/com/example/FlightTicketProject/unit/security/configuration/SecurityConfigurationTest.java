package com.example.FlightTicketProject.unit.security.configuration;

import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.exception.UserNotFoundException;
import com.example.FlightTicketProject.repository.UserRepository;
import com.example.FlightTicketProject.security.service.token.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    // Controversial moment, without it, I can't check the Authenticate test yet
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testShouldGiveAccessToRegisterResourcesWithoutAuthorization() throws Exception {
        String expectedFirstname = "firstname";
        String expectedLastname = "lastname";
        String expectedEmail = "test@gmail.com";
        String expectedPassword = "test";

        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setFirstname(expectedFirstname);
        requestDto.setLastname(expectedLastname);
        requestDto.setEmail(expectedEmail);
        requestDto.setPassword(expectedPassword);

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authentication/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGiveAccessToAuthenticateResourcesWithoutAuthorization() throws Exception {
        String expectedUserEmail = "test@gmail.com";
        String expectedPassword = "test";

        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setEmail(expectedUserEmail);
        requestDto.setPassword(expectedPassword);

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        String requestBody = objectMapper.writeValueAsString(requestDto);

        when(userRepository.findByEmail(expectedUserEmail)).thenReturn(Optional.of(expectedUser));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authentication/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldGiveAccessToAuthenticateResourcesWithoutAuthorizationButFail() throws Exception {
        String expectedUserEmail = "test@gmail.com";
        String expectedPassword = "test";
        String expectedErrorMessage = "User by email = " + expectedUserEmail + " not found";

        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setEmail(expectedUserEmail);
        requestDto.setPassword(expectedPassword);

        String requestBody = objectMapper.writeValueAsString(requestDto);

        when(userRepository.findByEmail(expectedUserEmail)).thenThrow(new UserNotFoundException("User by email = " + requestDto.getEmail() + " not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/authentication/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }

    @Test
    void testShouldGiveAccessToResourcesWithRoleUser() throws Exception {
        String expectedToken = "validToken";
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        when(jwtTokenService.extractUsername(expectedToken)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(expectedToken, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/user")
                        .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldCloseAccessToResourceWithUserRole() throws Exception {
        String expectedToken = "validToken";
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        when(jwtTokenService.extractUsername(expectedToken)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(expectedToken, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGiveAccessToResourcesWithRoleAdmin() throws Exception {
        String expectedToken = "validToken";
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.ADMIN);

        when(jwtTokenService.extractUsername(expectedToken)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(expectedToken, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isOk());
    }

    @Test
    void testCloseAccessToResourcesWithInvalidToken() throws Exception {
        String expectedToken = "invalidToken";
        String expectedUserEmail = "test@gmail.com";
        String expectedErrorMessage = "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.ADMIN);

        when(jwtTokenService.extractUsername(expectedToken)).thenThrow(new RuntimeException(expectedErrorMessage));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }

    @Test
    void testCloseAccessToResourcesWithExpiredToken() throws Exception {
        String expectedToken = "invalidToken";
        String expectedUserEmail = "test@gmail.com";
        String expectedErrorMessage = "JWT expired at 2023-03-08T19:41:38Z. Current time: 2023-03-08T19:42:04Z, a difference of 26975 milliseconds.  Allowed clock skew: 0 milliseconds.";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.ADMIN);

        when(jwtTokenService.extractUsername(expectedToken)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(expectedToken, expectedUser)).thenThrow(new RuntimeException(expectedErrorMessage));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + expectedToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }
}