package com.pz1.pai.client.repository;

import com.pz1.pai.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsClientByTaxpayerIdentNo(Long nip);
    boolean existsClientByName(String name);
    boolean existsClientByNameAndIdIsNot(String name, Long id);
}
