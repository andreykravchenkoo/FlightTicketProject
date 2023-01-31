package com.example.FlightTicketProject;

import com.example.FlightTicketProject.dto.response.ExternalApiFlightResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DummyTest {

    @Test
    void shouldDeserializeJson() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        ExternalApiFlightResponse root = objectMapper.readValue(json, ExternalApiFlightResponse.class);
        System.out.println(root);
    }

    String json = "{\n" +
            "              \"success\": true,\n" +
            "              \"data\": {\n" +
            "                  \"buckets\": [\n" +
            "                      {\n" +
            "                          \"id\": \"Best\",\n" +
            "                          \"name\": \"Best\",\n" +
            "                          \"items\": [\n" +
            "                              {\n" +
            "                                  \"id\": \"13870-2210110630--31915-0-11493-2210110900\",\n" +
            "                                  \"price\": {\n" +
            "                                      \"raw\": 75,\n" +
            "                                      \"formatted\": \"75 €\"\n" +
            "                                  },\n" +
            "                                  \"legs\": [\n" +
            "                                      {\n" +
            "                                          \"id\": \"13870-2210110630--31915-0-11493-2210110900\",\n" +
            "                                          \"origin\": {\n" +
            "                                              \"id\": \"MAD\",\n" +
            "                                              \"name\": \"Madrid\",\n" +
            "                                              \"displayCode\": \"MAD\",\n" +
            "                                              \"city\": \"Madrid\",\n" +
            "                                              \"isHighlighted\": false\n" +
            "                                          },\n" +
            "                                          \"destination\": {\n" +
            "                                              \"id\": \"FCO\",\n" +
            "                                              \"name\": \"Rome Fiumicino\",\n" +
            "                                              \"displayCode\": \"FCO\",\n" +
            "                                              \"city\": \"Rome\",\n" +
            "                                              \"isHighlighted\": false\n" +
            "                                          },\n" +
            "                                          \"durationInMinutes\": 150,\n" +
            "                                          \"stopCount\": 0,\n" +
            "                                          \"isSmallestStops\": true,\n" +
            "                                          \"departure\": \"2022-10-11T06:30:00\",\n" +
            "                                          \"arrival\": \"2022-10-11T09:00:00\",\n" +
            "                                          \"timeDeltaInDays\": 0,\n" +
            "                                          \"carriers\": {\n" +
            "                                              \"marketing\": [\n" +
            "                                                  {\n" +
            "                                                      \"id\": -31915,\n" +
            "                                                      \"logoUrl\": \"https://logos.skyscnr.com/images/airlines/favicon/FR.png\",\n" +
            "                                                      \"name\": \"Ryanair\"\n" +
            "                                                  }\n" +
            "                                              ],\n" +
            "                                              \"operationType\": \"fully_operated\"\n" +
            "                                          },\n" +
            "                                          \"segments\": [\n" +
            "                                              {\n" +
            "                                                  \"id\": \"13870-11493-2210110630-2210110900--31915\",\n" +
            "                                                  \"origin\": {\n" +
            "                                                      \"flightPlaceId\": \"MAD\",\n" +
            "                                                      \"parent\": {\n" +
            "                                                          \"flightPlaceId\": \"MADR\",\n" +
            "                                                          \"name\": \"Madrid\",\n" +
            "                                                          \"type\": \"City\"\n" +
            "                                                      },\n" +
            "                                                      \"name\": \"Madrid\",\n" +
            "                                                      \"type\": \"Airport\"\n" +
            "                                                  },\n" +
            "                                                  \"destination\": {\n" +
            "                                                      \"flightPlaceId\": \"FCO\",\n" +
            "                                                      \"parent\": {\n" +
            "                                                          \"flightPlaceId\": \"ROME\",\n" +
            "                                                          \"name\": \"Rome\",\n" +
            "                                                          \"type\": \"City\"\n" +
            "                                                      },\n" +
            "                                                      \"name\": \"Rome Fiumicino\",\n" +
            "                                                      \"type\": \"Airport\"\n" +
            "                                                  },\n" +
            "                                                  \"departure\": \"2022-10-11T06:30:00\",\n" +
            "                                                  \"arrival\": \"2022-10-11T09:00:00\",\n" +
            "                                                  \"durationInMinutes\": 150,\n" +
            "                                                  \"flightNumber\": \"9674\",\n" +
            "                                                  \"marketingCarrier\": {\n" +
            "                                                      \"id\": -31915,\n" +
            "                                                      \"name\": \"Ryanair\",\n" +
            "                                                      \"alternateId\": \"FR\",\n" +
            "                                                      \"allianceId\": 0\n" +
            "                                                  },\n" +
            "                                                  \"operatingCarrier\": {\n" +
            "                                                      \"id\": -31915,\n" +
            "                                                      \"name\": \"Ryanair\",\n" +
            "                                                      \"alternateId\": \"FR\",\n" +
            "                                                      \"allianceId\": 0\n" +
            "                                                  }\n" +
            "                                              }\n" +
            "                                          ]\n" +
            "                                      }\n" +
            "                                  ],\n" +
            "                                  \"isSelfTransfer\": false,\n" +
            "                                  \"eco\": {\n" +
            "                                      \"ecoContenderDelta\": 7.2873774\n" +
            "                                  },\n" +
            "                                  \"isMashUp\": false,\n" +
            "                                  \"hasFlexibleOptions\": false,\n" +
            "                                  \"score\": 3.14962\n" +
            "                              }\n" +
            "                          ]\n" +
            "                      }            \n" +
            "                  ]\n" +
            "              }\n" +
            "          }";

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
