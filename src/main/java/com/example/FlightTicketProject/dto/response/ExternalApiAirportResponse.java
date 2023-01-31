package com.example.FlightTicketProject.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalApiAirportResponse {

    private List<Data> data;

    @lombok.Data
    public static class Data {
        private String iata_code;
        private String name;
        private String city;
        private String country;
    }
}
