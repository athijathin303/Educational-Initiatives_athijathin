package com.example.controllers;
import com.example.core.MeetingRoom;
import com.example.utilities.Logger;

public class NotificationService implements IController {
    
    private final Logger logger = Logger.getInstance();

    @Override
    public void update(MeetingRoom room) {
        if (!room.isBooked() && room.getCurrentOccupancy() < 2 && room.getUnoccupiedSince() != null) {
            String msg = String.format("ATTENTION: Room %d booking was automatically released (5-minute rule).", room.getRoomId());
            System.out.println("-> NOTIFICATION: " + msg);
            logger.logInfo("Notification sent: " + msg);
        }
    }
}