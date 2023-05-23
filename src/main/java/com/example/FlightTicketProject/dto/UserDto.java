package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.User;
import com.example.FlightTicketProject.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotNull(message = "ID is required")
    private long id;

    @NotBlank(message = "Firstname is required")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @Email(
            regexp =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Email not valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "User role is required")
    private UserRole role;

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .firstname(this.firstname)
                .lastname(this.lastname)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
