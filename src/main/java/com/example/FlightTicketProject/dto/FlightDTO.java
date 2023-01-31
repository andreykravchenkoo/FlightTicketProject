package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.FareClassStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FlightDTO {

    private String id;

    private String origin;

    private String destination;

    private Date departure;

    private Date arrival;

    private double price;

    private FareClassStatus fareClass;

    private String carrier;
}
