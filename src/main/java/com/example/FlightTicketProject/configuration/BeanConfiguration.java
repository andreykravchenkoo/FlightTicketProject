package com.example.FlightTicketProject.configuration;

import com.example.FlightTicketProject.mapper.EntityMapper;
import com.example.FlightTicketProject.validator.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public EntityMapper entityMapper() {
        return new EntityMapper();
    }

    @Bean
    public EmailValidator emailValidator() {
        return new EmailValidator();
    }
}
