package com.example.utilities;

/** Thrown when a booking time overlaps with an existing booking. */
public class BookingConflictException extends CommandExecutionException {
    public BookingConflictException(String message) {
        super("Error: " + message);
    }
}