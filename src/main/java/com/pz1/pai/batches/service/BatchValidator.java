package com.pz1.pai.batches.service;

import com.pz1.pai.batches.repository.BatchRepository;
import com.pz1.pai.schedules.domain.Schedule;
import com.pz1.pai.schedules.repository.ScheduleRepository;
import com.pz1.pai.vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BatchValidator {

    private final BatchRepository batchRepository;
    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;

    public boolean vehicleExistCheck(final Long vehicleId){
        return vehicleRepository.existsById(vehicleId);
    }

    public boolean scheduleCheck(final Long vehicleId, final LocalDate date,
                                 final LocalTime startTime, final int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        List<Schedule> vehicleSchedules = scheduleRepository.findAllByVehicleIdAndDate(vehicleId, date);

        return vehicleSchedules.stream().anyMatch(schedule -> {
            if ((startTime.isBefore(schedule.getEndTime()) && startTime.isAfter(schedule.getStartTime())) ||
                    endTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime())){
                return true;
            }
            return false;
        });
    }

    public void batchRemover(final boolean toDelete, final Long id) {
        if (toDelete && batchRepository.existsById(id)) {
            batchRepository.deleteById(id);
        }
    }
}
