package com.example.utilities;

/** Base exception for command execution failures. */
public class CommandExecutionException extends RuntimeException {
    public CommandExecutionException(String message) {
        super(message);
    }
}