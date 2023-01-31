package com.example.FlightTicketProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private long id;

    private String name;

    private String username;

    private String email;

    private String password;
}
