package com.pz1.pai.batches.domain;

import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.schedules.domain.Schedule;
import com.pz1.pai.shared.IdEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batches")
public class Batch extends IdEntity {

    private double amount;
    private boolean done;
    private String dnNo;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
