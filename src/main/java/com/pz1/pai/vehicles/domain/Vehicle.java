package com.pz1.pai.vehicles.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pz1.pai.schedules.domain.Schedule;
import com.pz1.pai.shared.IdEntity;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends IdEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private VehicleType type;
    private String regNo;
    private double capacity;
    private double pumpLength;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Schedule> schedule = new ArrayList<>();
}
