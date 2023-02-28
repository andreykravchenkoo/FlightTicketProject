package com.example.FlightTicketProject.dto.response.goflightlabs;

import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.entity.FareClassStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String id;
        private String fareClass;
        private Price price;
        private List<Leg> legs;

        public FlightDto toDto() {
            return FlightDto.builder()
                    .id(this.id)
                    .origin(this.legs.get(0).getOrigin().getName())
                    .destination(this.legs.get(0).getDestination().getName())
                    .departure(this.legs.get(0).getDeparture())
                    .arrival(this.legs.get(0).getArrival())
                    .price(this.price.getRaw())
                    .fareClass(FareClassStatus.valueOf(this.fareClass.toUpperCase()))
                    .carrier(this.legs.get(0).getCarriers().getMarketing().get(0).getName())
                    .build();
        }
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
