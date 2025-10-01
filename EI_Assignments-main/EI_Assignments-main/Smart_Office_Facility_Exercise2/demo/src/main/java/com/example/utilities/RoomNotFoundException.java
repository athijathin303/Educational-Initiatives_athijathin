package com.example.utilities;

/** Thrown when a room with the given ID is not found. */
public class RoomNotFoundException extends CommandExecutionException {
    public RoomNotFoundException(int roomId) {
        super("Error: Room " + roomId + " does not exist.");
    }
}