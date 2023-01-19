package com.example.FlightTicketProject.mapper.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalApiFlightResponse {

    private com.example.FlightTicketProject.mapper.response.Data data;
}
