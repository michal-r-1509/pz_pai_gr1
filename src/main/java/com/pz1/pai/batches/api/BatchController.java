package com.pz1.pai.batches.api;

import com.pz1.pai.batches.dto.BatchDnPrintDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.batches.service.BatchService;
import com.pz1.pai.orders.service.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/batches")
public class BatchController {

    private final BatchService batchService;
    private final ApplicationEventPublisher eventPublisher;

    @PatchMapping("/{id}")
    ResponseEntity<Void> inverseBatchStatus(@PathVariable Long id){
        OrderStatus orderStatus = batchService.inverseStatus(id);
        eventPublisher.publishEvent(orderStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    ResponseEntity <BatchResponseDTO> readBatch(@PathVariable Long id){
        BatchResponseDTO batch = batchService.readBatch(id);
        if (batch == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(batch);
        }
    }

    @GetMapping("/{id}/print")
    ResponseEntity <BatchDnPrintDTO> readBatchToDnPrint(@PathVariable Long id){
        BatchDnPrintDTO batch = batchService.readBatchToDnPrint(id);
        if (batch == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(batch);
        }
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity <List<BatchResponseDTO>> readAllBatches(){
        return ResponseEntity.status(HttpStatus.OK).body(batchService.readAllBatches());
    }

    @GetMapping
    ResponseEntity <List<BatchResponseDTO>> readAllBatches(Sort sort){
        return ResponseEntity.status(HttpStatus.OK).body(batchService.readAllBatches(sort));
    }

    @GetMapping("/search/{orderId}")
    ResponseEntity <List<BatchResponseDTO>> readAllBatchesByOrderId(@PathVariable Long orderId){
        List<BatchResponseDTO> resultList = batchService.readAllBatchesByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
