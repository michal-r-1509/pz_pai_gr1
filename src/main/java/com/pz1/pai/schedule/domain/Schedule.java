package com.pz1.pai.schedule.domain;

import com.pz1.pai.batch.domain.Batch;
import com.pz1.pai.shared.BaseEntity;
import com.pz1.pai.vehicle.domain.Vehicle;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule extends BaseEntity {

    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @OneToOne(mappedBy = "schedule")
    private Batch batch;
}
