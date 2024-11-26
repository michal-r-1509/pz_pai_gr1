package com.pz1.pai.batch.domain;

import com.pz1.pai.order.domain.Order;
import com.pz1.pai.schedule.domain.Schedule;
import com.pz1.pai.shared.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "batches")
public class Batch extends BaseEntity {

    private double amount;
    private boolean done;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
}
