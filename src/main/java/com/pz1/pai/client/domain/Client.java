package com.pz1.pai.client.domain;

import com.pz1.pai.client.tool.ClientType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pz1.pai.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 50)
    private String streetAndNo;
    @Size(min = 5, max = 6)
    private String postCode;
    @Size(max = 20)
    private String city;
    private long taxpayerIdentNo;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClientType type;
}
