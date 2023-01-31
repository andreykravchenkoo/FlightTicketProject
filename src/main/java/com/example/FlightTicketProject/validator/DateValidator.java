package com.example.FlightTicketProject.validator;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DateValidator {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public boolean isDateValid(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
