package com.example.controllers;

import com.example.core.MeetingRoom;
import com.example.utilities.Logger;

/**
 * Concrete Observer that manages the room lights.
 */
public class LightController implements IController {
    
    private final Logger logger = Logger.getInstance();
    private final int roomId;

    public LightController(int roomId) {
        this.roomId = roomId;
        logger.logInfo("LightController initialized for Room " + roomId);
    }

    @Override
    public void update(MeetingRoom room) {
        if (room.getRoomId() != this.roomId) return;
        
        if (room.isOccupied()) {
            turnOnLights();
        } else {
            // Mandatory Requirement 5: Lights turn off if room is not occupied.
            turnOffLights(); 
        }
    }

    private void turnOnLights() {
        // Simulation logic
        logger.logInfo("Room " + roomId + " | Lights ON");
        // Output for positive case: "AC and lights turned on."
    }

    private void turnOffLights() {
        // Simulation logic
        logger.logInfo("Room " + roomId + " | Lights OFF");
        // Output for negative/release case: "AC and lights turned off."
    }
}