package com.pz1.pai.clients.service;

import com.pz1.pai.clients.domain.Client;
import com.pz1.pai.clients.domain.ClientType;
import com.pz1.pai.clients.repository.ClientRepository;
import com.pz1.pai.exceptions.DataNotFoundException;
import com.pz1.pai.exceptions.DuplicatedDataException;
import com.pz1.pai.orders.repository.OrderRepository;
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

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final ClientMapper clientMapper;

    @Override
    public Client saveClient(Client toSave) {
        Client client = clientMapper.newClientValidating(toSave);
        newClientBasicChecks(client);
        log.info("created client with name: {}, and type: {}", client.getName(), client.getType().toString());
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(final Long id, final Client toUpdate) {
        Client existClient = clientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("client", id));
        toUpdate.setType(clientMapper.clientType(toUpdate.getTaxpayerIdentNo()));
        existClientBasicChecks(id, toUpdate);
        clientMapper.existClientValidating(existClient, toUpdate);
        log.info("updated client with id: {}", existClient.getId());
        return clientRepository.save(existClient);
    }

    @Override
    public Client getClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new DataNotFoundException("client", id));
        log.info("reading client with id: {}", id);
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        log.info("reading all clients");
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllClients(final Sort sort) {
        log.info("reading all clients with sorting");
        return clientRepository.findAll(sort);
    }

    @Override
    public void deleteClient(final Long id) {
        if (clientRepository.existsById(id)) {
            if (orderRepository.existsOrderByClient_Id(id)) {
                throw new DuplicatedDataException("client in active order");
            } else {
                clientRepository.deleteById(id);
                log.info("deleted client with id: {}", id);
            }
        } else {
            throw new DataNotFoundException("client", id);
        }
    }

    private void newClientBasicChecks(final Client client){
        if (client.getType().equals(ClientType.BUSINESS) &&
                clientRepository.existsClientByTaxpayerIdentNo(client.getTaxpayerIdentNo())) {
            throw new DuplicatedDataException(
                    "client", String.valueOf(client.getTaxpayerIdentNo()));
        }
        if(clientRepository.existsClientByName(client.getName())){
            throw new DuplicatedDataException("client", client.getName());
        }
    }

    private void existClientBasicChecks(final Long id, final Client client){
        if (client.getType().equals(ClientType.BUSINESS) &&
                clientRepository.existsClientByTaxpayerIdentNo(client.getTaxpayerIdentNo())) {
            throw new DuplicatedDataException(
                    "client", String.valueOf(client.getTaxpayerIdentNo()));
        }
        if(clientRepository.existsClientByNameAndIdIsNot(client.getName(), id)){
            throw new DuplicatedDataException("client", client.getName());
        }
    }

}
