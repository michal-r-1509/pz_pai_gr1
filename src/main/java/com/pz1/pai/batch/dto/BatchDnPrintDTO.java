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
public class BatchDnPrintDTO {
    private Long id;
    private String dnNo;

    private double amount;
    private LocalTime time;

    private String siteAddress;
    private LocalDate date;
    private String concreteClass;

    private String clientName;
    private String taxPayIdentNo;

    private String vehicleName;
    private String vehicleRegNo;
    private VehicleType vehicleType;
}
