package com.pz1.pai.vehicle.domain;

import lombok.Getter;

@Getter
public enum VehicleType {

    MIXER(1),
    MIXER_PUMP(2),
    PUMP(3);

    private final int value;

    VehicleType(final int value) {
        this.value = value;
    }
}
