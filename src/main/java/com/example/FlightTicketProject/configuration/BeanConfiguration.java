package com.example.FlightTicketProject.configuration;

import com.example.FlightTicketProject.mapper.EntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public EntityMapper entityMapper() {
        return new EntityMapper();
    }
}
