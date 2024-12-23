package com.pz1.pai.order.service;

import com.pz1.pai.batch.dto.BatchRequestDTO;
import com.pz1.pai.batch.dto.BatchResponseDTO;
import com.pz1.pai.order.dto.OrderRequestDTO;
import com.pz1.pai.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    OrderResponseDTO saveOrder(final OrderRequestDTO toSave);
    List<BatchResponseDTO> saveOrUpdateBatches(final Long id, final List<BatchRequestDTO> toSave);
    OrderResponseDTO updateOrder(final Long id, final OrderRequestDTO toSave);
    void changeOrderStatus(boolean status, final Long id);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrder(final Long id);
    List<OrderResponseDTO> getAllOrders(final Sort sort);
    List<OrderResponseDTO> getAllOrdersByState(final boolean status, final Sort sort);
    void deleteOrder(final Long id);
}
