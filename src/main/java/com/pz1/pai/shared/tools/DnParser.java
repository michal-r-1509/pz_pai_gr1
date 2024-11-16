package com.pz1.pai.shared.tools;

import org.springframework.stereotype.Component;

@Component
public record DnParser() {

    public String toString(Long id) {
        return String.format("%05d", id);
    }
}
