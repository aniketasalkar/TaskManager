package com.example.taskmanager.exceptions;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message){
        super(message);
    }
}
