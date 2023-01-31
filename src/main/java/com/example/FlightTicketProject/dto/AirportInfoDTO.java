package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirportInfoDTO {

    private String IATACode;

    private String airportName;

    private String city;

    private String country;
}
