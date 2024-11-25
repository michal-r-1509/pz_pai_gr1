package com.pz1.pai.vehicles.service;

import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.exceptions.DuplicatedDataException;
import com.pz1.pai.exceptions.IllegalActionException;
import com.pz1.pai.schedules.repository.ScheduleRepository;
import com.pz1.pai.vehicles.domain.Vehicle;
import com.pz1.pai.vehicles.dto.VehicleRequestDTO;
import com.pz1.pai.vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public Vehicle saveVehicle(final VehicleRequestDTO toSave) {
        Vehicle vehicle = vehicleMapper.newVehicleValidation(toSave);
        if (vehicleRepository.existsByRegNo(vehicle.getRegNo())) {
            throw new DuplicatedDataException("vehicle", vehicle.getRegNo());
        }
        log.info("created vehicle with registry number: {}", vehicle.getRegNo());
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(final Long id, final VehicleRequestDTO toUpdate) {
        Vehicle existVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("vehicle", id));
        vehicleMapper.existVehicleValidation(toUpdate, existVehicle);
        if (vehicleRepository.existsByRegNoAndIdIsNot(existVehicle.getRegNo(), id)) {
            throw new DuplicatedDataException("vehicle", existVehicle.getRegNo());
        }
        log.info("updated vehicle with id: {}", id);
        return vehicleRepository.save(existVehicle);
    }

    @Override
    public Vehicle readVehicle(final Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("vehicle", id));
        log.info("reading vehicle with id: {}", id);
        return vehicle;
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        log.info("reading all vehicles");
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> readAllVehicles(final Sort sort) {
        log.info("reading all vehicles with sorting");
        return vehicleRepository.findAll(sort);
    }

    @Override
    public void deleteVehicle(final Long id) {
        if (vehicleRepository.existsById(id)){
            if (scheduleRepository.existsScheduleByVehicleId(id)){
                throw new IllegalActionException("vehicle in order batch");
            }else{
                vehicleRepository.deleteById(id);
                log.info("vehicle with id: {} deleted", id);
            }
        }else{
            throw new DataNotFoundException("vehicle", id);
        }
    }
}
