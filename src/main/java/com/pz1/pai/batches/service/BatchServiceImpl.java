package com.pz1.pai.batches.service;

import com.pz1.pai.batches.domain.Batch;
import com.pz1.pai.batches.dto.BatchDnPrintDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.batches.repository.BatchRepository;
import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.orders.service.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;
    private final BatchMapper batchMapper;

    @Override
    public OrderStatus inverseStatus(final Long id) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("orderBatch", id));
        batch.setDone(!batch.isDone());
        batchRepository.save(batch);
        log.info("changed status for batch with id {} to {}", id, batch.isDone());

        Long orderId = batch.getOrder().getId();
        double orderAmount = batch.getOrder().getAmount();
        double batchesAmount = 0;
        List<Batch> batches = batchRepository.findAllByOrderId(orderId);

        boolean batchesStatus = batches.stream().allMatch(Batch::isDone);
        if (batchesStatus){
            for (Batch entity : batches) {
                batchesAmount += entity.getAmount();
            }
        }

        return OrderStatus.changingOrderStatus(batchesStatus && orderAmount == batchesAmount, orderId);
    }

    @Override
    public BatchResponseDTO readBatch(final Long id) {
        Batch batch = batchRepository.findById(id).orElseThrow(() -> new DataNotFoundException("batch", id));
        List<Batch> batchList = new ArrayList<>();
        batchList.add(batch);
        log.info("reading batch with id: {}", id);
        return batchMapper.toBatchResponse(batchList).get(0);
    }

    @Override
    public BatchDnPrintDTO readBatchToDnPrint(final Long id) {
        Batch batch = batchRepository.findById(id).orElseThrow(() -> new DataNotFoundException("batch", id));
        log.info("reading batch with id: {}", id);
        return batchMapper.toBatchDnPrintResponse(batch);
    }

    @Override
    public List<BatchResponseDTO> readAllBatches() {
        List<Batch> batches = batchRepository.findAll();
        log.info("reading all batches");
        return batchMapper.toBatchResponse(batches);
    }

    @Override
    public List<BatchResponseDTO> readAllBatchesByOrderId(final Long orderId) {
        List<Batch> batches = batchRepository.findAllByOrderId(orderId);
        log.info("reading all batches by order id");
        return batchMapper.toBatchResponse(batches);
    }

    @Override
    public void deleteBatch(final Long id) {
        if (batchRepository.existsById(id)) {
            batchRepository.deleteById(id);
            log.info("batch with id: {} deleted", id);
        } else {
            throw new DataNotFoundException("batch", id);
        }
    }
}
