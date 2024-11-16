package com.pz1.pai.exceptions;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(String.format("%s not found", message));
    }
    public DataNotFoundException(String dataType, Long id) {
        super(String.format("%s with id %d not found", dataType, id));
    }

    public DataNotFoundException(String dataType, String dataName) {
        super(String.format("%s %s not found", dataType, dataName));
    }
}
