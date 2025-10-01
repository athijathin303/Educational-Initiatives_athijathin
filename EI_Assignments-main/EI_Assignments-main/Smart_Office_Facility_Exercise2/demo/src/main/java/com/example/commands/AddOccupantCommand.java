package com.example.commands;

import com.example.core.MeetingRoom;
import com.example.core.SmartOfficeHub;
import com.example.utilities.InvalidCommandArgumentException;
import com.example.utilities.Logger;

public class AddOccupantCommand implements ICommand {

    private final SmartOfficeHub hub;
    private final int roomId;
    private final int count;
    private final Logger logger = Logger.getInstance();

    public AddOccupantCommand(int roomId, int count) {
        this.hub = SmartOfficeHub.getInstance();
        this.roomId = roomId;
        
        if (count < 0) {
            throw new InvalidCommandArgumentException("Occupancy count cannot be negative.");
        }
        this.count = count;
    }

    @Override
    public String execute() {
        MeetingRoom room = hub.getRoom(roomId);
        
        String result = room.setOccupancy(count);
        
        logger.logInfo(String.format("Room %d occupancy updated to %d.", roomId, count));
        return result;
    }
}