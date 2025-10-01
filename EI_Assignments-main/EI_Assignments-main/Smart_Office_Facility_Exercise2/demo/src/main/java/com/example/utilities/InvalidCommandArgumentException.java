package com.example.utilities;

/** Thrown when a command is executed with invalid or missing parameters. */
public class InvalidCommandArgumentException extends CommandExecutionException {
    public InvalidCommandArgumentException(String message) {
        super("Error: Invalid command format or argument: " + message);
    }
}