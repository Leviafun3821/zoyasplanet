package com.zoyasplanet.englishapp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDTO {

    private Long id;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Due date cannot be null")
    private LocalDate dueDate;

    private LocalDate paidDate;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Status cannot be null")
    private PaymentStatus status;

    public enum PaymentStatus {
        PENDING, PAID, OVERDUE
    }
}
