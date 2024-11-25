package com.pz1.pai.clients.repository;

import com.pz1.pai.clients.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsClientByTaxpayerIdentNo(String txp);
    boolean existsClientByName(String name);
    boolean existsClientByNameAndIdIsNot(String name, Long id);
}
