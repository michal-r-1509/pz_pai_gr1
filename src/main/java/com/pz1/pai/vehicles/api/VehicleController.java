package com.pz1.pai.vehicles.api;

import com.pz1.pai.vehicles.domain.Vehicle;
import com.pz1.pai.vehicles.dto.VehicleRequestDTO;
import com.pz1.pai.vehicles.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/vehicles")
class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    ResponseEntity<Vehicle> saveVehicle(@RequestBody @Valid VehicleRequestDTO toSave) {
        Vehicle result = vehicleService.saveVehicle(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Vehicle> patchVehicle(@PathVariable Long id, @RequestBody VehicleRequestDTO toUpdate) {
        Vehicle result = vehicleService.updateVehicle(id, toUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<Vehicle> readVehicle(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.readVehicle(id));
    }

    @GetMapping
    ResponseEntity<List<Vehicle>> readAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.readAllVehicles());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
