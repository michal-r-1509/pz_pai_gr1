package com.pz1.pai.archive.service;

import com.pz1.pai.archive.domain.ArchivedBatch;
import com.pz1.pai.archive.repository.ArchiveRepository;
import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.exceptions.IllegalActionException;
import com.pz1.pai.orders.domain.Order;
import com.pz1.pai.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final OrderRepository orderRepository;
    private final ArchiveMapper archiveMapper;

    public void saveToArchive(final Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("order", orderId));
        if (order.isDone()) {
            List<ArchivedBatch> archivedBatches = order.getBatches().stream()
                    .map(archiveMapper::toArchive)
                    .collect(Collectors.toList());
            archiveRepository.saveAll(archivedBatches);
            log.info("batches for order with id {} archived", orderId);
        } else {
            throw new IllegalActionException("not all batches are completed");
        }
    }

    public List<ArchivedBatch> readAllArchivedBatches() {
        log.info("reading all archived batches with sorting");
        return archiveRepository.findAll().stream()
                .sorted(Comparator.comparing(ArchivedBatch::getTime))
                .sorted(Comparator.comparing(ArchivedBatch::getDate))
                .collect(Collectors.toList());
    }
}
