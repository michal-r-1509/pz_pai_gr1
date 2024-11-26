package com.pz1.pai.vehicle.service;

import com.pz1.pai.exceptions.ElementConflictException;
import com.pz1.pai.exceptions.ElementNotFoundException;
import com.pz1.pai.schedule.repository.ScheduleRepository;
import com.pz1.pai.vehicle.domain.Vehicle;
import com.pz1.pai.vehicle.dto.VehicleRequestDTO;
import com.pz1.pai.vehicle.repository.VehicleRepository;
import com.pz1.pai.vehicle.tool.VehicleMapper;
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

    private final VehicleRepository repository;
    private final VehicleMapper mapper;
    private final ScheduleRepository scheduleRepository;

    @Override
    public Vehicle saveVehicle(final VehicleRequestDTO toSave) {
        Vehicle vehicle = mapper.newVehicleValidation(toSave);
        if (repository.existsByRegNo(vehicle.getRegNo())) {
            throw new ElementConflictException("vehicle", "registry number", vehicle.getRegNo());
        }
        log.info("created vehicle with registry number: {}", vehicle.getRegNo());
        return repository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(final Long id, final VehicleRequestDTO toUpdate) {
        Vehicle existVehicle = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("vehicle", id));
        mapper.existVehicleValidation(toUpdate, existVehicle);
        if (repository.existsByRegNoAndIdIsNot(existVehicle.getRegNo(), id)) {
            throw new ElementConflictException("vehicle", "registry number", existVehicle.getRegNo());
        }
        log.info("updated vehicle with id: {}", id);
        return repository.save(existVehicle);
    }

    @Override
    public Vehicle readVehicle(final Long id) {
        Vehicle vehicle = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("vehicle", id));
        log.info("reading vehicle with id: {}", id);
        return vehicle;
    }

    @Override
    public List<Vehicle> readAllVehicles() {
        log.info("reading all vehicles");
        return repository.findAll();
    }

    @Override
    public List<Vehicle> readAllVehicles(final Sort sort) {
        log.info("reading all vehicles with sorting");
        return repository.findAll(sort);
    }

    @Override
    public void deleteVehicle(final Long id) {
        if (repository.existsById(id)){
            if (scheduleRepository.existsScheduleByVehicleId(id)){
                throw new ElementConflictException("vehicle in order batch");
            }else{
                repository.deleteById(id);
                log.info("vehicle with id: {} deleted", id);
            }
        }else{
            throw new ElementNotFoundException("vehicle", id);
        }
    }

    @Override
    public boolean existById(final Long id){
        return repository.existsById(id);
    }

}
