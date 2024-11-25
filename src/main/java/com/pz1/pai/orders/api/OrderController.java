package com.pz1.pai.orders.api;

import com.pz1.pai.archive.service.ArchiveService;
import com.pz1.pai.batches.dto.BatchRequestDTO;
import com.pz1.pai.batches.dto.BatchResponseDTO;
import com.pz1.pai.orders.dto.OrderRequestDTO;
import com.pz1.pai.orders.dto.OrderResponseDTO;
import com.pz1.pai.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/orders")
class OrderController {

    private final OrderService orderService;
    private final ArchiveService archiveService;

    @PostMapping
    ResponseEntity<OrderResponseDTO> saveOrder(@RequestBody @Valid OrderRequestDTO toSave) {
        OrderResponseDTO result = orderService.saveOrder(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}/batches")
    ResponseEntity<List<BatchResponseDTO>> updateOrderBatch(@PathVariable Long id,
                                                            @RequestBody @Valid List<BatchRequestDTO> toSave) {
        List<BatchResponseDTO> batches = orderService.saveOrUpdateBatches(id, toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(batches);
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequestDTO toUpdate) {
        OrderResponseDTO updated = orderService.updateOrder(id, toUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PatchMapping("/{id}/archive")
    ResponseEntity<Void> createArchiveOrder(@PathVariable(name = "id") Long orderId){
        archiveService.saveToArchive(orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<OrderResponseDTO>> readOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping
    ResponseEntity<List<OrderResponseDTO>> readOrders(Sort sort) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders(sort));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> readOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrder(id));
    }

    @GetMapping("/search")
    ResponseEntity<List<OrderResponseDTO>> readOrders(
            @RequestParam(required = false, defaultValue = "false") boolean status, Sort sort) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersByState(status, sort));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}