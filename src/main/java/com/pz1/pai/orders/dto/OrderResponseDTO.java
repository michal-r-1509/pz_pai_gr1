package com.pz1.pai.orders.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class OrderResponseDTO {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private double amount;
    private String siteAddress;
    private String description;
    private boolean pump;
    private boolean done;
    private String orderNo;
}
