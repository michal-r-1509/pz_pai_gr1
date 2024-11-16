package com.pz1.pai.archive.repository;

import com.pz1.pai.archive.domain.ArchivedBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends JpaRepository<ArchivedBatch, Long> {

}