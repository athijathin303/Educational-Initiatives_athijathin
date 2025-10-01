package com.example.controllers;

import com.example.core.MeetingRoom;
import com.example.utilities.Logger;

public class ACController implements IController {
    
    private final Logger logger = Logger.getInstance();
    private final int roomId;

    public ACController(int roomId) {
        this.roomId = roomId;
        logger.logInfo("ACController initialized for Room " + roomId);
    }

    @Override
    public void update(MeetingRoom room) {
        if (room.getRoomId() != this.roomId) return;

        if (room.isOccupied()) {
            turnOnAC();
        } else {
            // Mandatory Requirement 5: AC turns off if room is not occupied.
            turnOffAC();
        }
    }

    private void turnOnAC() {
        // Simulation logic
        logger.logInfo("Room " + roomId + " | AC ON");
    }

    private void turnOffAC() {
        // Simulation logic
        logger.logInfo("Room " + roomId + " | AC OFF");
    }
}