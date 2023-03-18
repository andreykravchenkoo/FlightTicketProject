package com.example.FlightTicketProject.deserializer;

import com.example.FlightTicketProject.dto.AirportInfoDto;
import com.example.FlightTicketProject.dto.FlightDto;
import com.example.FlightTicketProject.entity.FareClassStatus;
import com.example.FlightTicketProject.exception.ExternalApiParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoflightlabsResponseCustomDeserializer {

    private final ObjectMapper objectMapper;

    public Set<FlightDto> flightResponseDeserialize(String json, FareClassStatus fareClass) {
        log.info("Parsing flights response from external API");
        try {
            return Optional.ofNullable(objectMapper.readTree(json).get("data"))
                    .map(dataNode -> dataNode.get("buckets"))
                    .stream()
                    .flatMap(bucketsNode -> StreamSupport.stream(bucketsNode.spliterator(), false))
                    .map(bucketNode -> bucketNode.get("items"))
                    .flatMap(itemsNode -> StreamSupport.stream(itemsNode.spliterator(), false))
                    .map(itemNode -> {
                        String id = itemNode.get("id").asText();
                        double price = itemNode.get("price").get("raw").asDouble();
                        String origin = itemNode.get("legs").get(0).get("origin").get("name").asText();
                        String destination = itemNode.get("legs").get(0).get("destination").get("name").asText();
                        Date departure;
                        Date arrival;
                        try {
                            departure = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(itemNode.get("legs").get(0).get("departure").asText());
                            arrival = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(itemNode.get("legs").get(0).get("arrival").asText());
                        } catch (ParseException e) {
                            log.error("Error parsing date format a flights response from external API, check response");
                            throw new ExternalApiParseException(e.getMessage());
                        }
                        String carrier = itemNode.get("legs").get(0).get("carriers").get("marketing").get(0).get("name").asText();
                        return new FlightDto(id, origin, destination, departure, arrival, price, fareClass, carrier);
                    })
                    .collect(Collectors.toSet());
        } catch (JsonProcessingException e) {
            log.error("Error parsing flights response from external API, check response");
            throw new ExternalApiParseException(e.getMessage());
        }
    }

    public List<AirportInfoDto> airportResponseDeserialize(String json) {
        log.info("Parsing airports response from external API");
        try {
            return Optional.ofNullable(objectMapper.readTree(json).get("data"))
                    .map(data -> StreamSupport.stream(data.spliterator(), false)
                            .map(airportNode -> new AirportInfoDto(
                                    airportNode.get("iata_code").asText(),
                                    airportNode.get("name").asText(),
                                    airportNode.get("city").asText(),
                                    airportNode.get("country").asText()))
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        } catch (JsonProcessingException e) {
            log.error("Error parsing airports response from external API, check response");
            throw new ExternalApiParseException(e.getMessage());
        }
    }
}
