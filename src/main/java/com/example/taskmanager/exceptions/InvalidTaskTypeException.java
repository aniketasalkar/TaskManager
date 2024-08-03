package com.example.taskmanager.exceptions;

public class InvalidTaskTypeException extends RuntimeException {
    public InvalidTaskTypeException(String message) {
        super(message);
    }
}
