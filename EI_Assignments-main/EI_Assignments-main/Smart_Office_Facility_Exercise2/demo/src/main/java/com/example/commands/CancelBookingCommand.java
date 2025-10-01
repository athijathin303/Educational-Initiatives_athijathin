package com.example.commands;

import com.example.core.MeetingRoom;
import com.example.core.SmartOfficeHub;
import com.example.utilities.CommandExecutionException;
import com.example.utilities.Logger;

public class CancelBookingCommand implements ICommand {

    private final SmartOfficeHub hub;
    private final int roomId;
    private final Logger logger = Logger.getInstance();

    public CancelBookingCommand(int roomId) {
        this.hub = SmartOfficeHub.getInstance();
        this.roomId = roomId;
    }

    @Override
    public String execute() {
        MeetingRoom room = hub.getRoom(roomId);
        
        if (!room.isBooked()) {
            throw new CommandExecutionException(
                String.format("Room %d is not booked. Cannot cancel booking.", roomId));
        }
        
        room.releaseBooking();
        logger.logInfo(String.format("Booking for Room %d cancelled.", roomId));
        return String.format("Booking for Room %d cancelled successfully.", roomId);
    }
}