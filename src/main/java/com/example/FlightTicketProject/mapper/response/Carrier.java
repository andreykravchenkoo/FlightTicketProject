package com.example.FlightTicketProject.mapper.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Carrier {
    private List<Marketing> marketing;
}
