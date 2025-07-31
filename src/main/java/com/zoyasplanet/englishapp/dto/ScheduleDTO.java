package com.zoyasplanet.englishapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDTO {

    private Long id;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Time cannot be null")
    private LocalTime time;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private Long teacherId;

    @NotNull(message = "Task ID cannot be null")
    private Long taskId;
}
