package com.example.notes.exception;

public class ExistsException extends RuntimeException{
    public ExistsException(String message){
        super(message);
    }
}
