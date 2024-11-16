package com.pz1.pai.orders.service;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.batches.dto.BatchRequestDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.batches.repository.BatchRepository;
import com.pz1.pai.batches.service.BatchMapper;
import com.pz1.pai.batches.service.BatchValidator;
import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.orders.dto.OrderRequestDTO;
import com.pz1.pai.orders.dto.OrderResponseDTO;
import com.pz1.pai.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BatchRepository batchRepository;
    private final OrderMapper orderMapper;
    private final BatchMapper batchMapper;
    private final BatchValidator batchValidator;

    @Override
    public OrderResponseDTO saveOrder(final OrderRequestDTO toSave) {
        Order order = orderMapper.newOrderDataValidating(toSave);
        Order saved = orderRepository.save(order);
        saved.setOrderNo(generateOrderNo(saved.getId()));
        Order savedWithNo = orderRepository.save(saved);
        log.info("created order with id: {}", order.getId());
        return orderMapper.toResponse(savedWithNo);
    }

    @Override
    public List<BatchResponseDTO> saveOrUpdateBatches(final Long id, final List<BatchRequestDTO> toSave) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("order", id));

        List<Batch> batches = toSave.stream()
                .filter(batchReqDTO -> batchValidator.vehicleExistCheck(batchReqDTO.getVehicleId()))
                .peek(batchReqDTO -> batchValidator.batchRemover(batchReqDTO.isToDelete(), batchReqDTO.getId()))
                .filter(batchReqDTO -> !batchValidator.scheduleCheck(
                        batchReqDTO.getVehicleId(),
                        order.getDate(),
                        batchReqDTO.getTime(),
                        batchReqDTO.getDuration())
                )
                .filter(batchReqDTO -> !batchReqDTO.isToDelete())
                .peek(batchReqDTO -> {
                    if (batchRepository.existsById(batchReqDTO.getId())) {
                        Batch batch = batchRepository.findById(batchReqDTO.getId()).get();
                        batchMapper.updateBatch(batch, batchReqDTO);
                        batchRepository.save(batch);
                    }
                })
                .filter(batchReqDTO -> !batchRepository.existsById(batchReqDTO.getId()))
                .map(batchReqDTO -> batchMapper.toBatch(batchReqDTO, order))
                .collect(Collectors.toList());

        batchRepository.saveAll(batches);
        List<Batch> byOrderBatches = batchRepository.findAllByOrderId(order.getId());
        List<Batch> collected = byOrderBatches.stream()
                .peek(batch -> batch.setDnNo(generateDnNo(batch.getId())))
                .peek(batchRepository::save)
                .collect(Collectors.toList());
        return batchMapper.toBatchResponse(collected);
    }

    @Override
    public OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave) {
        Order existOrder = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("order", id));
        orderMapper.existOrderDataValidating(toSave, existOrder);
        orderRepository.save(existOrder);
        log.info("updated order with id: {}", existOrder.getId());
        return orderMapper.toResponse(existOrder);
    }

    @Override
    public void changeOrderStatus(boolean status, final Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("order", id));
        order.setDone(status);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        log.info("reading all orders");
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrder(final Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("order", id));
        log.info("reading order with id: {}", id);
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByState(final boolean status) {
        log.info("reading all orders with sorting by status");
        return getAllOrders().stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(final Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            log.info("order with id: {} deleted", id);
        } else {
            throw new DataNotFoundException("order", id);
        }
    }

    private String generateOrderNo(Long id) {
        return String.format("Z%05d", id);
    }

    private String generateDnNo(Long id) {
        return String.format("%07d", id);
    }
}
