package com.example.FlightTicketProject.dto;

import com.example.FlightTicketProject.entity.Payment;
import com.example.FlightTicketProject.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    @NotNull(message = "ID is required")
    private long id;

    @NotBlank(message = "Owner is required")
    private String owner;

    @Min(value = 0, message = "Sum must be positive")
    private double sum;

    @NotNull(message = "Date is required")
    private Date date;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;

    public Payment toEntity() {
        return Payment.builder()
                .id(this.id)
                .owner(this.owner)
                .sum(this.sum)
                .date(this.date)
                .status(this.status)
                .build();
    }
}
