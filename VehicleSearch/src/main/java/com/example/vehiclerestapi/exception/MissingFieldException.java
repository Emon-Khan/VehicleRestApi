package com.example.vehiclerestapi.exception;

public class MissingFieldException extends Exception{
    public MissingFieldException(String message){
        super(message);
    }
}
