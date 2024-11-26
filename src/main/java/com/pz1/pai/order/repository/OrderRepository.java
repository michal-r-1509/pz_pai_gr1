package com.pz1.pai.order.repository;

import com.pz1.pai.order.domain.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    boolean existsOrderByClient_Id(Long id);
}
