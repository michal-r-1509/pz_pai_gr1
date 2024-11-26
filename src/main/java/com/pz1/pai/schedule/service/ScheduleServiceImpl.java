package com.pz1.pai.schedule.service;

import com.pz1.pai.schedule.domain.Schedule;
import com.pz1.pai.schedule.repository.ScheduleRepository;
import com.pz1.pai.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void saveSchedule(final Schedule schedule) {
        repository.save(schedule);
    }

    @Override
    public void saveSchedule(final Schedule schedule, final Long vehicleId) {
        schedule.setVehicle(vehicleRepository.findById(vehicleId).get());
        repository.save(schedule);
    }

    @Override
    public List<Schedule> getScheduleByVehicleIdAndDate(final Long vehicleId, final LocalDate date) {
        return repository.findAllByVehicleIdAndDate(vehicleId, date);
    }
}
