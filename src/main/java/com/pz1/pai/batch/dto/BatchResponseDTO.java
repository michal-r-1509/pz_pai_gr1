package com.pz1.pai.batch.dto;

import com.pz1.pai.vehicle.domain.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BatchResponseDTO {
    private Long id;
    private double amount;
    private int duration;
    private LocalTime time;
    private boolean done;

    private String siteAddress;
    private LocalDate date;
    private Long orderId;

    private String clientName;

    private Long vehicleId;
    private String vehicleName;
    private String vehicleRegNo;
    private VehicleType vehicleType;
}
