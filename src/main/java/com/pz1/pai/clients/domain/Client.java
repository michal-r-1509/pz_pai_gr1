package com.pz1.pai.clients.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pz1.pai.shared.IdEntity;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends IdEntity {

    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 50)
    private String streetAndNo;
    @Size(max = 6)
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Invalid postal code format")
    private String postCode;
    @Size(max = 20)
    private String city;
    @Size(max = 13)
    @Pattern(regexp = "^(\\d{3}-\\d{3}-\\d{2}-\\d{2})|^$", message = "Invalid taxpayer identify number format")
    private String taxpayerIdentNo;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private ClientType type;
}
