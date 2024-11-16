package com.pz1.pai.schedules.repository;

import com.pz1.pai.schedules.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByVehicleIdAndDate(final Long vehicle_id, final LocalDate date);
    boolean existsScheduleByVehicleId(Long id);
}
