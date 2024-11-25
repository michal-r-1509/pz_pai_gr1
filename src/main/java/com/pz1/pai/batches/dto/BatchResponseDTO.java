package com.pz1.pai.batches.dto;

import com.pz1.pai.vehicles.domain.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
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
