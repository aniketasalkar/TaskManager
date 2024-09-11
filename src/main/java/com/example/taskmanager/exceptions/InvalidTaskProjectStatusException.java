package com.example.taskmanager.exceptions;

public class InvalidTaskProjectStatusException extends RuntimeException {
    public InvalidTaskProjectStatusException(String message) {
        super(message);
    }
}
