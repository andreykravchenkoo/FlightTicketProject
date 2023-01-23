package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirportInfoDTO {

    private String IATACode;
    private String airportName;
    private String city;
    private String country;

    public AirportInfoDTO(ExternalApiAirportResponse.Data data) {
        this.IATACode = data.getIata_code();
        this.airportName = data.getName();
        this.city = data.getCity();
        this.country = data.getCountry();
    }
}
