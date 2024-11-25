package com.pz1.pai.archive.domain;

import com.pz1.pai.shared.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "archived_batches")
public class ArchivedBatch extends IdEntity {

    private String dnNo;
    private LocalDate date;
    private LocalTime time;
    private String siteAddress;
    private String clientName;
    private String clientAddress;
    private String clientPostCode;
    private String clientCity;
    private String clientTxpIN;
    private String vehicleType;
    private String vehicleName;
    private String vehicleRegNo;
    private String concreteClass;
    private double amount;
}
