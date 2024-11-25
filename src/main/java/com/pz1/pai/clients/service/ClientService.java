package com.pz1.pai.clients.service;

import com.pz1.pai.clients.domain.Client;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ClientService {
    Client saveClient(Client toSave);
    Client updateClient(final Long id, final Client toUpdate);
    Client getClient(Long id);
    List<Client> getAllClients();
    List<Client> getAllClients(final Sort sort);
    void deleteClient(final Long id);
}
