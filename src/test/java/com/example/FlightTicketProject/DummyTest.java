package com.example.FlightTicketProject;

import com.example.FlightTicketProject.dto.FlightDTO;
import com.example.FlightTicketProject.entity.Flight;
import com.example.FlightTicketProject.mapper.response.ExternalApiAirportResponse;
import com.example.FlightTicketProject.mapper.response.ExternalApiFlightResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DummyTest {

    @Test
    void shouldDeserializeJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        ExternalApiAirportResponse root = objectMapper.readValue(json, ExternalApiAirportResponse.class);
        System.out.println(root);

//        List<ExternalApiFlightResponse.Item> flights = root.getData().getBuckets().stream().flatMap(x -> x.getItems().stream()).collect(Collectors.toList());
//
//        System.out.println(flights);
    }

    String json = "{\"success\":false,\"message\":\"invalid access key\"}";

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Root {
        boolean success;
        Data data;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        List<Bucket> buckets;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bucket {
        String id;
        String name;
        List<Item> items;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        String id;
        Price price;
        List<Leg> legs;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Leg {
        String id;
        Origin origin;
        Destination destination;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Origin {
        String id;
        String name;
        String displayCode;
        String city;
        boolean isHighlighted;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Destination {
        String id;
        String name;
        String displayCode;
        String city;
        boolean isHighlighted;
        // getters and setters
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Price {
        int raw;
        String formatted;
    }
}
