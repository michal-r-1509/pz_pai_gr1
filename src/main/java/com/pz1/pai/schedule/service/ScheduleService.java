package com.pz1.pai.schedule.service;

import com.pz1.pai.schedule.domain.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    void saveSchedule(final Schedule schedule);
    void saveSchedule(final Schedule schedule, final Long vehicleId);
    List<Schedule> getScheduleByVehicleIdAndDate(final Long vehicleId, final LocalDate date);
}
