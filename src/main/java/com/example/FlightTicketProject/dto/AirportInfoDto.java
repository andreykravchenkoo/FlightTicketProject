package com.example.FlightTicketProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirportInfoDto {

    private String airportCode;

    private String airportName;

    private String city;

    private String country;
}
