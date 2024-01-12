package com.example.vehiclerestapi.exception;

public class MandatoryFieldMissingException extends Exception {
    public MandatoryFieldMissingException(String allErrors) {
        super(allErrors);
    }
}
