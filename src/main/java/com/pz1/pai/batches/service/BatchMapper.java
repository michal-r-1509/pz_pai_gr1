package com.pz1.pai.batches.service;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.batches.dto.BatchDnPrintDTO;
import com.pz1.pai.batches.dto.BatchRequestDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.schedules.domain.Schedule;
import com.pz1.pai.shared.tools.DnParser;
import com.pz1.pai.vehicles.domain.Vehicle;
import com.pz1.pai.vehicles.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BatchMapper {

    private final VehicleRepository vehicleRepository;
    private final ScheduleMapper scheduleMapper;
    private final DnParser dnParser;

    public Batch toBatch(final BatchRequestDTO batchRequestDTO, final Order order) {
        return Batch.builder()
                .amount(batchRequestDTO.getAmount())
                .done(false)
                .schedule(scheduleMapper.toSchedule(order,
                        batchRequestDTO,
                        vehicleRepository.findById(batchRequestDTO.getVehicleId())
                                .orElseThrow(() -> new DataNotFoundException("vehicle", batchRequestDTO.getVehicleId()))
                ))
                .order(order)
                .build();
    }

    public void updateBatch(final Batch existBatch, final BatchRequestDTO batchRequestDTO) {
        existBatch.setAmount(batchRequestDTO.getAmount());
        Schedule schedule = existBatch.getSchedule();
        schedule.setStartTime(batchRequestDTO.getTime());
        schedule.setEndTime(batchRequestDTO.getTime().plusMinutes(batchRequestDTO.getDuration()));
        schedule.setVehicle(vehicleRepository.findById(batchRequestDTO.getVehicleId())
                .orElseThrow(() -> new DataNotFoundException("vehicle", batchRequestDTO.getVehicleId())));
    }

    public List<BatchResponseDTO> toBatchResponse(final List<Batch> batches) {
        return batches.stream()
                .map(batch -> {
                    Schedule schedule = batch.getSchedule();
                    Vehicle vehicle = schedule.getVehicle();
                    long duration = Duration.between(schedule.getStartTime(), schedule.getEndTime()).toMinutes();
                    return BatchResponseDTO.builder()
                            .id(batch.getId())
                            .amount(batch.getAmount())
                            .duration((int) duration)
                            .time(schedule.getStartTime())
                            .done(batch.isDone())
                            .siteAddress(batch.getOrder().getSiteAddress())
                            .date(batch.getOrder().getDate())
                            .orderId(batch.getOrder().getId())
                            .vehicleId(vehicle.getId())
                            .vehicleName(vehicle.getName())
                            .vehicleRegNo(vehicle.getRegNo())
                            .vehicleType(vehicle.getType())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public BatchDnPrintDTO toBatchDnPrintResponse(final Batch batch) {
        return BatchDnPrintDTO.builder()
                .id(batch.getId())
                .dnNo(dnParser.toString(batch.getId()))
                .amount(batch.getAmount())
                .time(batch.getSchedule().getStartTime())
                .siteAddress(batch.getOrder().getSiteAddress())
                .date(batch.getOrder().getDate())
                .concreteClass(batch.getOrder().getConcreteClass())
                .vehicleName(batch.getSchedule().getVehicle().getName())
                .vehicleRegNo(batch.getSchedule().getVehicle().getRegNo())
                .vehicleType(batch.getSchedule().getVehicle().getType())
                .build();
    }
}
