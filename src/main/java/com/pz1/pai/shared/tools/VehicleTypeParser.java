package com.pz1.pai.shared.tools;

import com.pz1.pai.vehicles.domain.VehicleType;
import org.springframework.stereotype.Component;

@Component
public record VehicleTypeParser() {

    public String toString(VehicleType type){
        return switch (type.getValue()) {
            case 1 -> "gruszka";
            case 2 -> "pompo-gruszka";
            case 3 -> "pompa";
            default -> throw new IllegalArgumentException("bad vehicle type");
        };
    }
}
