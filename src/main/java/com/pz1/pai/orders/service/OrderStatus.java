package com.pz1.pai.orders.service;

import lombok.Getter;

@Getter
public class OrderStatus {

    private final boolean status;
    private final Long orderId;

    public OrderStatus(final boolean status, final Long orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public static OrderStatus changingOrderStatus(final boolean status, final Long orderId){
        return new OrderStatus(status, orderId);
    }
}
