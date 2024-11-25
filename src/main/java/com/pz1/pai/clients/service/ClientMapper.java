package com.pz1.pai.clients.service;

import com.pz1.pai.clients.domain.Client;
import com.pz1.pai.clients.domain.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientMapper {

    public Client newClientValidating(final Client toSave) {
        return Client.builder()
                .name(toSave.getName())
                .streetAndNo(toSave.getStreetAndNo())
                .postCode(toSave.getPostCode())
                .city(toSave.getCity())
                .taxpayerIdentNo(toSave.getTaxpayerIdentNo())
                .type(clientType(toSave.getTaxpayerIdentNo()))
                .build();
    }

    public void existClientValidating(final Client existClient, final Client toUpdate) {
        existClient.setName(toUpdate.getName());
        existClient.setStreetAndNo(toUpdate.getStreetAndNo());
        existClient.setPostCode(toUpdate.getPostCode());
        existClient.setCity(toUpdate.getCity());
        existClient.setTaxpayerIdentNo(toUpdate.getTaxpayerIdentNo());
        existClient.setType(clientType(toUpdate.getTaxpayerIdentNo()));
    }

    public ClientType clientType(final String taxpayerIdentNo){
        if (taxpayerIdentNo == null || taxpayerIdentNo.isBlank()){
            return ClientType.INDIVIDUAL;
        }else {
            return ClientType.BUSINESS;
        }
    }
}
