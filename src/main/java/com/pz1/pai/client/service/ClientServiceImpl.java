package com.pz1.pai.client.service;

import com.pz1.pai.client.domain.Client;
import com.pz1.pai.client.repository.ClientRepository;
import com.pz1.pai.client.tool.ClientMapper;
import com.pz1.pai.client.tool.ClientType;
import com.pz1.pai.exceptions.ElementConflictException;
import com.pz1.pai.exceptions.ElementNotFoundException;
import com.pz1.pai.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final OrderRepository orderRepository;

    @Override
    public Client saveClient(Client toSave) {
        Client client = mapper.newClientValidating(toSave);
        newClientBasicChecks(client);
        log.info("created client with name: {}, and type: {}", client.getName(), client.getType().toString());
        return repository.save(client);
    }

    @Override
    public Client updateClient(final Long id, final Client toUpdate) {
        Client existClient = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("client", id));
        toUpdate.setType(mapper.clientType(toUpdate.getTaxpayerIdentNo()));
        existClientBasicChecks(id, toUpdate);
        mapper.existClientValidating(existClient, toUpdate);
        log.info("updated client with id: {}", existClient.getId());
        return repository.save(existClient);
    }

    @Override
    public Client getClient(Long id) {
        Client client = repository.findById(id).orElseThrow(() -> new ElementNotFoundException("client", id));
        log.info("reading client with id: {}", id);
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        log.info("reading all clients");
        return repository.findAll();
    }

    @Override
    public List<Client> getAllClients(final Sort sort) {
        log.info("reading all clients with sorting");
        return repository.findAll(sort);
    }

    @Override
    public void deleteClient(final Long id) {
        if (repository.existsById(id)) {
            if (orderRepository.existsOrderByClient_Id(id)) {
                throw new ElementConflictException("client in active order");
            } else {
                repository.deleteById(id);
                log.info("deleted client with id: {}", id);
            }
        } else {
            throw new ElementNotFoundException("client", id);
        }
    }

    private void newClientBasicChecks(final Client client){
        if (client.getType().equals(ClientType.BUSINESS) &&
                repository.existsClientByTaxpayerIdentNo(client.getTaxpayerIdentNo())) {
            throw new ElementConflictException(
                    "client", "taxpayer identity number", String.valueOf(client.getTaxpayerIdentNo()));
        }
        if(repository.existsClientByName(client.getName())){
            throw new ElementConflictException("client", "name", client.getName());
        }
    }

    private void existClientBasicChecks(final Long id, final Client client){
        if (client.getType().equals(ClientType.BUSINESS) &&
                repository.existsClientByTaxpayerIdentNo(client.getTaxpayerIdentNo())) {
            throw new ElementConflictException(
                    "client", "taxpayer identity number", String.valueOf(client.getTaxpayerIdentNo()));
        }
        if(repository.existsClientByNameAndIdIsNot(client.getName(), id)){
            throw new ElementConflictException("client", "name", client.getName());
        }
    }

}
