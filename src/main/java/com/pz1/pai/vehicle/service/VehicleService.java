package com.pz1.pai.vehicle.service;

import com.pz1.pai.vehicle.domain.Vehicle;
import com.pz1.pai.vehicle.dto.VehicleRequestDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle(final VehicleRequestDTO toSave);
    Vehicle updateVehicle(final Long id, final VehicleRequestDTO toUpdate);
    Vehicle readVehicle(final Long id);
    List<Vehicle> readAllVehicles();
    List<Vehicle> readAllVehicles(final Sort sort);
    void deleteVehicle(final Long id);
    boolean existById(final Long id);
}