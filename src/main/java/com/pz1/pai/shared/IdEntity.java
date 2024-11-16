package com.pz1.pai.shared;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
}
