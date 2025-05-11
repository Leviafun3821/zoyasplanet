package com.zoyasplanet.englishapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TaskDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 255, message = "Link cannot exceed 255 characters")
    private String link;

    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status;

    @DecimalMin(value = "0.0", inclusive = false, message = "Payment amount must be greater than 0")
    private Double paymentAmount;

    private LocalDate paymentDueDate;

    private Long userId;
}
