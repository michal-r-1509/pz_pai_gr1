package com.pz1.pai.vehicle.repository;

import com.pz1.pai.vehicle.domain.Vehicle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByRegNo(String regNo);
    boolean existsByRegNoAndIdIsNot(String regNo, Long id);

}