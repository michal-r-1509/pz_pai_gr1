package com.pz1.pai.order.repository;

import com.pz1.pai.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsOrderByClient_Id(Long id);
}
