package com.pz1.pai.batches.service;

import com.pz1.pai.batches.dto.BatchRequestDTO;
import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.schedules.domain.Schedule;
import com.pz1.pai.vehicles.domain.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMapper {

    public Schedule toSchedule(final Order order, final BatchRequestDTO batchReqDTO, final Vehicle vehicle) {
        return Schedule.builder()
                .date(order.getDate())
                .startTime(batchReqDTO.getTime())
                .endTime(batchReqDTO.getTime().plusMinutes(batchReqDTO.getDuration()))
                .vehicle(vehicle)
                .build();
    }

}
