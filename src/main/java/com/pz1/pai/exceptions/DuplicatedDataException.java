package com.pz1.pai.exceptions;

public class DuplicatedDataException extends RuntimeException{

    public DuplicatedDataException(String message) {
        super(String.format("%s already exists", message));
    }

    public DuplicatedDataException(String dataType, Long id) {
        super(String.format("%s with id %d already exists", dataType, id));
    }

    public DuplicatedDataException(String dataType, String dataName) {
        super(String.format("%s %s already exists", dataType, dataName));
    }
}
