package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class PaymentDTO {

    @NotNull(message = "ID is required")
    private long id;

    @Min(value = 0, message = "Sum must be positive")
    private double sum;

    @NotBlank(message = "Owner is required")
    private String owner;

    @NotNull(message = "Date is required")
    private Date date;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}
