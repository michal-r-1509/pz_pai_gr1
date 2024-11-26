package com.pz1.pai.archive.controller;

import com.pz1.pai.archive.service.ArchiveService;
import com.pz1.pai.archive.domain.ArchivedBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/archives")
public class ArchiveController {

    private final ArchiveService service;

    @PatchMapping("/{id}")
    ResponseEntity<Void> createArchiveOrder(@PathVariable(name = "id") Long orderId){
        service.saveToArchive(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<ArchivedBatch>> readAllArchivedBatches(){
        return ResponseEntity.ok(service.readAllArchivedBatches());
    }

    @GetMapping
    ResponseEntity<List<ArchivedBatch>> readAllArchivedBatches(Sort sort){
        return ResponseEntity.ok(service.readAllArchivedBatches(sort));
    }
}
