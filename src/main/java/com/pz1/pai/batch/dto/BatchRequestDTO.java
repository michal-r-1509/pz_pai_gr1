package com.pz1.pai.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@Getter
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
