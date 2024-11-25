package com.pz1.pai.clients.api;

import com.pz1.pai.clients.domain.Client;
import com.pz1.pai.clients.service.ClientService;
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
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    ResponseEntity<Client> saveClient(@RequestBody @Valid Client toSave) {
        Client result = clientService.saveClient(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Client> patchClient(@PathVariable Long id, @RequestBody Client toUpdate) {
        Client result = clientService.updateClient(id, toUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Client> readClient(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClient(id));
    }

    @GetMapping(params = {"!sort"})
    ResponseEntity<List<Client>> readAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @GetMapping
    ResponseEntity<List<Client>> readAllClients(Sort sort) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients(sort));
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
