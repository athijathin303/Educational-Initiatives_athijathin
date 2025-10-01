package com.example.commands;

import com.example.core.BookingDetails;
import com.example.core.MeetingRoom;
import com.example.core.SmartOfficeHub;
import com.example.utilities.BookingConflictException;
import com.example.utilities.InvalidCommandArgumentException;
import com.example.utilities.Logger;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Handles the booking of a specific meeting room.
 * Corresponds to console input: "Block room 1 09:00 60"
 */
public class BookRoomCommand implements ICommand {

    private final SmartOfficeHub hub;
    private final int roomId;
    private final LocalTime startTime;
    private final int durationMinutes;
    private final Logger logger = Logger.getInstance();

    public BookRoomCommand(int roomId, String startTimeStr, int durationMinutes) {
        this.hub = SmartOfficeHub.getInstance();
        this.roomId = roomId;
        this.durationMinutes = durationMinutes;

        try {
            this.startTime = LocalTime.parse(startTimeStr);
        } catch (DateTimeParseException e) {
            throw new InvalidCommandArgumentException("Invalid time format. Please use HH:mm (e.g., 09:00).");
        }
        
        if (durationMinutes <= 0) {
            throw new InvalidCommandArgumentException("Duration must be a positive number of minutes.");
        }
    }

    @Override
    public String execute() {
        MeetingRoom room = hub.getRoom(roomId);
        LocalTime newEndTime = startTime.plusMinutes(durationMinutes);
        
        if (room.checkConflict(startTime, newEndTime)) {
            throw new BookingConflictException(
                String.format("Room %d is already booked during this time (%s). Cannot book.", 
                roomId, room.getCurrentBooking().toString()));
        }

        BookingDetails newBooking = new BookingDetails(startTime, durationMinutes);
        room.setBooking(newBooking);
        
        logger.logInfo(String.format("Room %d booked: %s", roomId, newBooking.toString()));
        return String.format("Room %d booked from %s for %d minutes.", 
            roomId, startTime.toString(), durationMinutes);
    }
}