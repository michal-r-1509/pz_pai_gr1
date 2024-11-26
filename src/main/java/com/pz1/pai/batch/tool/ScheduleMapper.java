package com.pz1.pai.batch.tool;

import com.pz1.pai.order.domain.Order;
import com.pz1.pai.schedule.domain.Schedule;
import com.pz1.pai.vehicle.domain.Vehicle;
import com.pz1.pai.batch.dto.BatchRequestDTO;
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
