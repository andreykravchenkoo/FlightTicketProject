package com.example.FlightTicketProject.dto.response.goflightlabs;

import com.example.FlightTicketProject.dto.AirportInfoDto;
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

        public AirportInfoDto toDto() {
            return AirportInfoDto.builder()
                    .airportCode(this.iata_code)
                    .airportName(this.name)
                    .city(this.city)
                    .country(this.country)
                    .build();
        }
    }
}
