package com.example.FlightTicketProject.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.FlightTicketProject.entity.*;
import com.example.FlightTicketProject.exception.ResourceNotFound;
import com.example.FlightTicketProject.service.FlightService;
import com.example.FlightTicketProject.service.PaymentService;
import com.example.FlightTicketProject.service.TicketService;
import com.example.FlightTicketProject.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "test")
class BookingTicketFacadeTest {

    @Mock private FlightService flightService;

    @Mock private TicketService ticketService;

    @Mock private PaymentService paymentService;

    @Mock private UserService userService;

    @InjectMocks private BookingTicketFacade bookingTicketFacade;

    @Test
    void testShouldSuccessfulyBookTicket() {
        String expectedFlightId = "1";
        String expectedSeat = "F10";

        Flight expectedFlight = new Flight();
        expectedFlight.setId(expectedFlightId);

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setFirstname("firstname");
        expectedUser.setLastname("lastname");
        expectedUser.setEmail("email");

        String expectedOwner = expectedUser.getFirstname() + " " + expectedUser.getLastname();

        Payment expectedPayment = new Payment();
        expectedPayment.setId(1L);
        expectedPayment.setOwner(expectedOwner);
        expectedPayment.setStatus(PaymentStatus.NEW);

        when(flightService.findById(expectedFlightId)).thenReturn(expectedFlight);
        when(userService.findByEmail()).thenReturn(expectedUser);

        Ticket ticket = bookingTicketFacade.bookTicket(expectedFlightId, expectedSeat);

        assertAll(
                () -> assertNotNull(ticket),
                () -> assertEquals(expectedOwner, ticket.getOwner()),
                () -> assertEquals(expectedPayment.getOwner(), ticket.getPayment().getOwner()),
                () -> assertEquals(expectedPayment.getStatus(), ticket.getPayment().getStatus()),
                () -> assertEquals(expectedUser, ticket.getUser()),
                () -> assertEquals(expectedFlight, ticket.getFlight()),
                () -> assertEquals(ticket, ticket.getPayment().getTicket()));

        verify(flightService).findById(expectedFlightId);
        verify(userService).findByEmail();
        verify(ticketService).save(ticket);
        verify(paymentService).save(ticket.getPayment());
        verify(flightService).save(expectedFlight);
        verify(userService).save(expectedUser);
    }

    @Test
    void testShouldFailBookTicketWhenFLightNotFound() {
        String expectedFlightId = "1";
        String expectedSeat = "F10";
        String expectedErrorMessage = "Flight with ID = " + expectedFlightId + " will not be found";

        when(flightService.findById(expectedFlightId))
                .thenThrow(new ResourceNotFound(expectedErrorMessage));
        ResourceNotFound exception =
                assertThrows(
                        ResourceNotFound.class,
                        () -> bookingTicketFacade.bookTicket(expectedFlightId, expectedSeat));

        assertEquals(expectedErrorMessage, exception.getLocalizedMessage());
    }
}
