package com.pz1.pai.batch.repository;

import com.pz1.pai.batch.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    List<Batch> findAllByOrderId(Long orderId);
}
