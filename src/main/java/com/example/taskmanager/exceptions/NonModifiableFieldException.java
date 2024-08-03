package com.example.taskmanager.exceptions;

public class NonModifiableFieldException extends RuntimeException {
    public NonModifiableFieldException(String message) {
        super(message);
    }
}
