package com.pz1.pai.exceptions;

public class TaxpayerIdentityNumberException extends RuntimeException {
    public TaxpayerIdentityNumberException() {
        super("invalid taxpayer identity number");
    }
}
