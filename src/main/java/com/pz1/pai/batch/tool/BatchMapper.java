package com.pz1.pai.batch.tool;

import com.pz1.pai.archive.tool.DnParser;
import com.pz1.pai.archive.tool.TaxpayerIdentNoParser;
import com.pz1.pai.batch.domain.Batch;
import com.pz1.pai.batch.dto.BatchRequestDTO;
import com.pz1.pai.client.domain.Client;
import com.pz1.pai.batch.dto.BatchDnPrintDTO;
import com.pz1.pai.batch.dto.BatchResponseDTO;
import com.pz1.pai.order.domain.Order;
import com.pz1.pai.schedule.domain.Schedule;
import com.pz1.pai.vehicle.domain.Vehicle;
import com.pz1.pai.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BatchMapper {

    private final VehicleService vehicleService;
    private final ScheduleMapper scheduleMapper;
    private final DnParser dnParser;
    private final TaxpayerIdentNoParser tpIdentNoParser;

    public Batch toBatch(final BatchRequestDTO batchRequestDTO, final Order order) {
        return Batch.builder()
                .amount(batchRequestDTO.getAmount())
                .done(false)
                .schedule(scheduleMapper.toSchedule(order, batchRequestDTO,
                        vehicleService.readVehicle(batchRequestDTO.getVehicleId())))
                .order(order)
                .build();
    }

    public void updateBatch(final Batch existBatch, final BatchRequestDTO batchRequestDTO) {
        existBatch.setAmount(batchRequestDTO.getAmount());
        Schedule schedule = existBatch.getSchedule();
        schedule.setStartTime(batchRequestDTO.getTime());
        schedule.setEndTime(batchRequestDTO.getTime().plusMinutes(batchRequestDTO.getDuration()));
        schedule.setVehicle(vehicleService.readVehicle(batchRequestDTO.getVehicleId()));
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
                            .clientName(batch.getOrder().getClient().getName())
                            .vehicleId(vehicle.getId())
                            .vehicleName(vehicle.getName())
                            .vehicleRegNo(vehicle.getRegNo())
                            .vehicleType(vehicle.getType())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public BatchDnPrintDTO toBatchDnPrintResponse(final Batch batch) {
        Client client = batch.getOrder().getClient();
        long tpIdentNoVal = client.getTaxpayerIdentNo();
        String tpIdentNo = tpIdentNoVal == 0 ? "" : tpIdentNoParser.toString(tpIdentNoVal);
        return BatchDnPrintDTO.builder()
                .id(batch.getId())
                .dnNo(dnParser.toString(batch.getId()))
                .amount(batch.getAmount())
                .time(batch.getSchedule().getStartTime())
                .siteAddress(batch.getOrder().getSiteAddress())
                .date(batch.getOrder().getDate())
                .concreteClass(batch.getOrder().getConcreteClass())
                .clientName(client.getName())
                .taxPayIdentNo(tpIdentNo)
                .vehicleName(batch.getSchedule().getVehicle().getName())
                .vehicleRegNo(batch.getSchedule().getVehicle().getRegNo())
                .vehicleType(batch.getSchedule().getVehicle().getType())
                .build();
    }
}
