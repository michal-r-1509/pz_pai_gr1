package com.pz1.pai.order.event;

import com.pz1.pai.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class ChangedOrderStatusListener {

    private final OrderService orderService;

    @Async
    @EventListener
    public void changedStatus(OrderStatus orderStatus){
        changingStatus(orderStatus);
    }

    private void changingStatus(final OrderStatus orderStatus) {
        orderService.changeOrderStatus(orderStatus.isStatus(), orderStatus.getOrderId());
        log.info("status of order with id " + orderStatus.getOrderId() + " is changed to " + orderStatus.isStatus());
    }
}
