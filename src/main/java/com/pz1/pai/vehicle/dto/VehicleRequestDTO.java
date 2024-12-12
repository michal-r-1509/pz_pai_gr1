package com.pz1.pai.vehicle.dto;

import com.pz1.pai.vehicle.domain.VehicleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class VehicleRequestDTO {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    private VehicleType type;
    @NotBlank
    @Size(min = 2, max = 10)
    private String regNo;
    @DecimalMin("0.0")
    @DecimalMax("30.0")
    private double capacity;
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private double pumpLength;
}
