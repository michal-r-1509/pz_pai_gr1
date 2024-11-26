package com.pz1.pai.batch.service;

import com.pz1.pai.batch.dto.BatchDnPrintDTO;
import com.pz1.pai.batch.dto.BatchResponseDTO;
import com.pz1.pai.order.event.OrderStatus;
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
