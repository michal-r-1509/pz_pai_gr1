package com.pz1.pai.batches.repository;

import com.pz1.pai.batches.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findAllByOrderId(Long orderId);
}
