package com.app.billingsystem.exception;

public class BadApiRequestException extends  RuntimeException{
    public BadApiRequestException(String message){
        super(message);
    }
}
