package com.pz1.pai.archive.api;

import com.pz1.pai.archive.domain.ArchivedBatch;
import com.pz1.pai.archive.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/archives")
public class ArchiveController {

    private final ArchiveService archiveService;

    @PatchMapping("/{id}")
    ResponseEntity<Void> createArchiveOrder(@PathVariable(name = "id") Long orderId){
        archiveService.saveToArchive(orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    ResponseEntity<List<ArchivedBatch>> readAllArchivedBatches(){
        return ResponseEntity.status(HttpStatus.OK).body(archiveService.readAllArchivedBatches());
    }

}
