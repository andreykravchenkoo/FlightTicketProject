package com.example.FlightTicketProject.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalApiFlightResponse {

    private Data data;

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        private List<Bucket> buckets;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bucket {
        private List<Item> items;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String id;
        private String fareClass;
        private Price price;
        private List<Leg> legs;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Price {
        private double raw;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Leg {
        private Origin origin;
        private Destination destination;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private Date departure;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private Date arrival;
        private Carrier carriers;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Origin {
        private String name;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Destination {
        private String name;

    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Carrier {
        private List<Marketing> marketing;
    }

    @lombok.Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Marketing {
        private String name;
    }
}
