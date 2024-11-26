package com.pz1.pai.order.domain;

import com.pz1.pai.client.domain.Client;
import com.pz1.pai.batch.domain.Batch;
import com.pz1.pai.shared.BaseEntity;
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
public class Order extends BaseEntity {

    private LocalDate date;
    private LocalTime time;
    private double amount;
    private String concreteClass;
    private String siteAddress;
    private String description;
    private boolean pump;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Batch> batches = new ArrayList<>();
}