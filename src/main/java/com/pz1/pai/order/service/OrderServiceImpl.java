package com.pz1.pai.order.service;

import com.pz1.pai.client.domain.Client;
import com.pz1.pai.client.service.ClientService;
import com.pz1.pai.exceptions.ElementNotFoundException;
import com.pz1.pai.batch.dto.BatchRequestDTO;
import com.pz1.pai.batch.dto.BatchResponseDTO;
import com.pz1.pai.batch.repository.BatchRepository;
import com.pz1.pai.batch.tool.BatchMapper;
import com.pz1.pai.batch.tool.BatchValidator;
import com.pz1.pai.batch.domain.Batch;
import com.pz1.pai.order.domain.Order;
import com.pz1.pai.order.dto.OrderRequestDTO;
import com.pz1.pai.order.dto.OrderResponseDTO;
import com.pz1.pai.order.repository.OrderRepository;
import com.pz1.pai.order.tool.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final BatchMapper batchMapper;
    private final BatchRepository batchRepository;
    private final BatchValidator batchValidator;
    private final ClientService clientService;

    @Override
    public OrderResponseDTO saveOrder(final OrderRequestDTO toSave) {
        Client client = clientService.getClient(toSave.getClientId());
        Order order = mapper.newOrderDataValidating(toSave, client);
        repository.save(order);
        log.info("created order with id: {}", order.getId());
        return mapper.toResponse(order);
    }

    @Override
    public OrderResponseDTO saveOrder(final Order toSave) {
        repository.save(toSave);
        log.info("created order with id: {}", toSave.getId());
        return mapper.toResponse(toSave);
    }

    @Override
    public List<BatchResponseDTO> saveOrUpdateBatches(final Long id, final List<BatchRequestDTO> toSave) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("order", id));

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
        return batchMapper.toBatchResponse(byOrderBatches);
    }

    @Override
    public OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave) {
        Order existOrder = repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("order", id));
        Client client = clientService.getClient(toSave.getClientId());
        mapper.existOrderDataValidating(toSave, client, existOrder);
        repository.save(existOrder);
        log.info("updated order with id: {}", existOrder.getId());
        return mapper.toResponse(existOrder);
    }

    @Override
    public void changeOrderStatus(boolean status, final Long id){
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        order.setDone(status);
        repository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        log.info("reading all orders");
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Order::getTime))
                .sorted(Comparator.comparing(Order::getDate))
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrder(final Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("order", id));
        log.info("reading order with id: {}", id);
        return mapper.toResponse(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders(final Sort sort) {
        log.info("reading all orders with sorting");
        return repository.findAll(sort).stream()
                .map(order -> mapper.toResponse(order))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort) {
        log.info("reading all orders with sorting by status");
        return getAllOrders(sort).stream()
                .filter(order -> order.isDone() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(final Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("order with id: {} deleted", id);
        } else {
            throw new ElementNotFoundException("order", id);
        }
    }
}
