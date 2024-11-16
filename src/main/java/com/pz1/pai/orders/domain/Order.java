package com.pz1.pai.orders.domain;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.shared.IdEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends IdEntity {

    private LocalDate date;
    private LocalTime time;
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    private boolean done;
    private String orderNo;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();
}