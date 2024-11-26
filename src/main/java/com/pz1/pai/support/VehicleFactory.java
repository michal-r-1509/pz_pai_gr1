package com.pz1.pai.support;

import com.pz1.pai.vehicle.dto.VehicleRequestDTO;
import com.pz1.pai.vehicle.domain.VehicleType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VehicleFactory {

    private final List<VehicleRequestDTO> vehicles = new ArrayList<>();

    public VehicleFactory() {
        this.vehicles.add(VehicleRequestDTO.builder()
                .name("Mercedes Benz")
                .regNo("AA9876")
                .type(VehicleType.MIXER)
                .capacity(9.0)
                .build());
        this.vehicles.add(VehicleRequestDTO.builder()
                .name("Star")
                .regNo("AA1111")
                .type(VehicleType.MIXER_PUMP)
                .capacity(9.0)
                .pumpLength(24.0)
                .build());
        this.vehicles.add(VehicleRequestDTO.builder()
                .name("Iveco")
                .regNo("ZZ0000")
                .type(VehicleType.PUMP)
                .capacity(9.0)
                .pumpLength(36.0)
                .build());
    }

}
