package com.example.FlightTicketProject.security.configuration;

import com.example.FlightTicketProject.dto.request.AuthenticationRequestDto;
import com.example.FlightTicketProject.dto.request.RegisterRequestDto;
import com.example.FlightTicketProject.dto.response.AuthenticationResponseDto;
import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;
import com.example.FlightTicketProject.exception.UserNotFoundException;
import com.example.FlightTicketProject.security.service.AuthenticationService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationService authenticationService;

    // Controversial moment, without it, I can't check the Authenticate test yet
    @MockBean
    private AuthenticationManager authenticationManager;

    private static final String EXPECTED_VALID_TOKEN = "validToken";

    private static final String EXPECTED_INVALID_TOKEN = "invalidToken";

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

        AuthenticationRequestDto expectedRequestDto = new AuthenticationRequestDto();
        expectedRequestDto.setEmail(expectedUserEmail);
        expectedRequestDto.setPassword(expectedPassword);

        AuthenticationResponseDto expectedResponseDto = new AuthenticationResponseDto();
        expectedResponseDto.setToken(EXPECTED_VALID_TOKEN);

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        String requestBody = objectMapper.writeValueAsString(expectedRequestDto);

        when(authenticationService.authenticate(expectedRequestDto)).thenReturn(expectedResponseDto);

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

        AuthenticationRequestDto expectedRequestDto = new AuthenticationRequestDto();
        expectedRequestDto.setEmail(expectedUserEmail);
        expectedRequestDto.setPassword(expectedPassword);

        AuthenticationResponseDto expectedResponseDto = new AuthenticationResponseDto();
        expectedResponseDto.setToken(EXPECTED_VALID_TOKEN);

        String requestBody = objectMapper.writeValueAsString(expectedRequestDto);

        when(authenticationService.authenticate(expectedRequestDto)).thenThrow(new UserNotFoundException("User by email = " + expectedRequestDto.getEmail() + " not found"));

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
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        when(jwtTokenService.extractUsername(EXPECTED_VALID_TOKEN)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(EXPECTED_VALID_TOKEN, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights/user")
                        .header("Authorization", "Bearer " + EXPECTED_VALID_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void testShouldCloseAccessToResourceWithUserRole() throws Exception {
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.USER);

        when(jwtTokenService.extractUsername(EXPECTED_VALID_TOKEN)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(EXPECTED_VALID_TOKEN, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + EXPECTED_VALID_TOKEN))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGiveAccessToResourcesWithRoleAdmin() throws Exception {
        String expectedUserEmail = "test@gmail.com";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.ADMIN);

        when(jwtTokenService.extractUsername(EXPECTED_VALID_TOKEN)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(EXPECTED_VALID_TOKEN, expectedUser)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + EXPECTED_VALID_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void testCloseAccessToResourcesWithInvalidToken() throws Exception {
        String expectedErrorMessage = "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted";

        when(jwtTokenService.extractUsername(EXPECTED_INVALID_TOKEN)).thenThrow(new RuntimeException(expectedErrorMessage));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + EXPECTED_INVALID_TOKEN))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }

    @Test
    void testCloseAccessToResourcesWithExpiredToken() throws Exception {
        String expectedUserEmail = "test@gmail.com";
        String expectedErrorMessage = "JWT expired at 2023-03-08T19:41:38Z. Current time: 2023-03-08T19:42:04Z, a difference of 26975 milliseconds.  Allowed clock skew: 0 milliseconds.";

        User expectedUser = new User();
        expectedUser.setEmail(expectedUserEmail);
        expectedUser.setRole(UserRole.ADMIN);

        when(jwtTokenService.extractUsername(EXPECTED_INVALID_TOKEN)).thenReturn(expectedUserEmail);
        when(userDetailsService.loadUserByUsername(expectedUserEmail)).thenReturn(expectedUser);
        when(jwtTokenService.isTokenValid(EXPECTED_INVALID_TOKEN, expectedUser)).thenThrow(new RuntimeException(expectedErrorMessage));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/flights")
                        .header("Authorization", "Bearer " + EXPECTED_INVALID_TOKEN))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertTrue(responseBody.contains(expectedErrorMessage));
    }
}