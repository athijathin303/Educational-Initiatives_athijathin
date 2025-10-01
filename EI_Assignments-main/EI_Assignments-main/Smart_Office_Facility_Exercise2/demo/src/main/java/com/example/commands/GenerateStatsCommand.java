package com.example.commands;

import com.example.core.SmartOfficeHub;
import com.example.utilities.CommandExecutionException;
import com.example.utilities.Logger;

public class GenerateStatsCommand implements ICommand {
    private final SmartOfficeHub hub;
    private final Logger logger = Logger.getInstance();

    public GenerateStatsCommand() {
        this.hub = SmartOfficeHub.getInstance();
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Room Usage Statistics ---").append("\n");
        int totalRooms = hub.getAllRooms().size();
        
        if (totalRooms == 0) {
            throw new CommandExecutionException("Office is not yet configured. Cannot show statistics.");
        }
        
        sb.append(String.format("Total Rooms Configured: %d\n", totalRooms));
        sb.append("-----------------------------\n");
        
        hub.getAllRooms().forEach((id, room) -> {
            String bookingStatus = room.isBooked() ? "BOOKED" : "FREE";
            int timesBooked = room.getTimesBooked(); 
            
            sb.append(String.format("Room %d | Bookings: %d | Current Status: %s | Occupancy: %d\n",
                id, timesBooked, bookingStatus, room.getCurrentOccupancy()));
        });
        
        logger.logInfo("Statistics report generated successfully.");
        return sb.toString();
    }
}