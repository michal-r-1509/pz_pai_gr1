package com.pz1.pai.orders.domain;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.clients.domain.Client;
import com.pz1.pai.shared.IdEntity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();
}