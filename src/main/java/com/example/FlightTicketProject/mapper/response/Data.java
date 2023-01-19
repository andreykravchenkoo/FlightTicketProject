package com.example.FlightTicketProject.mapper.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private List<Bucket> buckets;

}
