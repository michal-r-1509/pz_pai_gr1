package com.pz1.pai.client.repository;

import com.pz1.pai.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    boolean existsClientByTaxpayerIdentNo(Long nip);
    boolean existsClientByName(String name);
    boolean existsClientByNameAndIdIsNot(String name, Long id);
}
