package com.pz1.pai.batches.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class BatchRequestDTO {
    private Long id;
    @NotNull(message = "time cannot be null")
    private LocalTime time;
    @Min(0L)
    private double amount;
    @Min(0)
    @Max(720)
    private int duration;
    @NotNull
    private Long vehicleId;
    private boolean toDelete;
}
