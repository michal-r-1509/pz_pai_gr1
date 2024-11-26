package com.pz1.pai.batch.tool;

import com.pz1.pai.schedule.domain.Schedule;
import com.pz1.pai.batch.repository.BatchRepository;
import com.pz1.pai.schedule.service.ScheduleService;
import com.pz1.pai.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BatchValidator {
    private final BatchRepository repository;
    private final VehicleService vehicleService;
    private final ScheduleService scheduleService;

    public boolean vehicleExistCheck(final Long vehicleId){
        return vehicleService.existById(vehicleId);
    }

    public boolean scheduleCheck(final Long vehicleId, final LocalDate date,
                                 final LocalTime startTime, final int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        List<Schedule> vehicleSchedules = scheduleService.getScheduleByVehicleIdAndDate(vehicleId, date);
        boolean exist = vehicleSchedules.stream().anyMatch(schedule -> {
            if ((startTime.isBefore(schedule.getEndTime()) && startTime.isAfter(schedule.getStartTime())) ||
                    endTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime())){
                return true;
            }
            return false;
        });
        return exist;
    }

    public void batchRemover(final boolean toDelete, final Long id) {
        if (toDelete && repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
