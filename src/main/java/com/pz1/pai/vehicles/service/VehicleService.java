package com.pz1.pai.vehicles.service;

import com.pz1.pai.vehicles.domain.Vehicle;
import com.pz1.pai.vehicles.dto.VehicleRequestDTO;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle(final VehicleRequestDTO toSave);
    Vehicle updateVehicle(final Long id, final VehicleRequestDTO toUpdate);
    Vehicle readVehicle(final Long id);
    List<Vehicle> readAllVehicles();
    void deleteVehicle(final Long id);
}