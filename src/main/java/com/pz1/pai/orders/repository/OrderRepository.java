package com.pz1.pai.orders.repository;

import com.pz1.pai.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findTopByOrderByOrderNoDesc();
}
