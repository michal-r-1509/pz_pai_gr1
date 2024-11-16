package com.pz1.pai.orders.service;

import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.orders.dto.OrderRequestDTO;
import com.pz1.pai.orders.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order newOrderDataValidating(OrderRequestDTO toSave) {
        return Order.builder()
                .date(toSave.getDate())
                .time(toSave.getTime())
                .amount(toSave.getAmount())
                .concreteClass(toSave.getConcreteClass())
                .siteAddress(toSave.getSiteAddress())
                .description(toSave.getDescription())
                .pump(toSave.isPump())
                .done(false)
                .build();
    }

    public void existOrderDataValidating(OrderRequestDTO toSave, final Order existOrder) {
        existOrder.setDate(toSave.getDate());
        existOrder.setTime(toSave.getTime());
        existOrder.setAmount(toSave.getAmount());
        existOrder.setConcreteClass(toSave.getConcreteClass());
        existOrder.setSiteAddress(toSave.getSiteAddress());
        existOrder.setDescription(toSave.getDescription());
        existOrder.setPump(toSave.isPump());
    }

    public OrderResponseDTO toResponse(final Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .time(order.getTime())
                .amount(order.getAmount())
                .siteAddress(order.getSiteAddress())
                .description(order.getDescription())
                .pump(order.isPump())
                .done(order.isDone())
                .orderNo(order.getOrderNo())
                .build();
    }
}
