package com.pz1.pai.orders.service;

import com.pz1.pai.clients.domain.Client;
import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.orders.dto.OrderRequestDTO;
import com.pz1.pai.orders.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order newOrderDataValidating(OrderRequestDTO toSave, final Client client) {
        return Order.builder()
                .date(toSave.getDate())
                .time(toSave.getTime())
                .amount(toSave.getAmount())
                .concreteClass(toSave.getConcreteClass())
                .siteAddress(toSave.getSiteAddress())
                .description(toSave.getDescription())
                .pump(toSave.isPump())
                .done(false)
                .client(client)
                .build();
    }

    public void existOrderDataValidating(OrderRequestDTO toSave, final Client client, final Order existOrder) {
        existOrder.setDate(toSave.getDate());
        existOrder.setTime(toSave.getTime());
        existOrder.setAmount(toSave.getAmount());
        existOrder.setConcreteClass(toSave.getConcreteClass());
        existOrder.setSiteAddress(toSave.getSiteAddress());
        existOrder.setDescription(toSave.getDescription());
        existOrder.setPump(toSave.isPump());
        existOrder.setClient(client);
    }

    public OrderResponseDTO toResponse(final Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .time(order.getTime())
                .amount(order.getAmount())
                .clientId(order.getClient().getId())
                .clientName(order.getClient().getName())
                .siteAddress(order.getSiteAddress())
                .description(order.getDescription())
                .pump(order.isPump())
                .done(order.isDone())
                .orderNo(order.getOrderNo())
                .build();
    }
}
