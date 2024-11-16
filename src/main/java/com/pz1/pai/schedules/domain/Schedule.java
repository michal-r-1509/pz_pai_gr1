package com.pz1.pai.schedules.domain;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.shared.IdEntity;
import com.pz1.pai.vehicles.domain.Vehicle;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule extends IdEntity {

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
