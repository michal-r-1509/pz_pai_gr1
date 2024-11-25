package com.pz1.pai.batches.service;

import com.pz1.pai.batches.dto.BatchDnPrintDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.orders.service.OrderStatus;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BatchService {
    OrderStatus inverseStatus(final Long id);
    BatchResponseDTO readBatch(final Long id);
    BatchDnPrintDTO readBatchToDnPrint(final Long id);
    List<BatchResponseDTO> readAllBatches();
    List<BatchResponseDTO> readAllBatches(final Sort sort);
    List<BatchResponseDTO> readAllBatchesByOrderId(final Long orderId);
    void deleteBatch(final Long id);
}
