package com.example.strategies;

import com.example.core.MeetingRoom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Concrete Strategy implementing the 5-minute timeout rule (Mandatory Req 4).
 */
public class FiveMinuteTimeoutStrategy implements IBookingReleaseStrategy {

    private static final long RELEASE_DURATION_MINUTES = 5;

    @Override
    public boolean shouldRelease(MeetingRoom room, LocalDateTime currentTime) {
        // Only check if the room is currently booked AND not occupied.
        if (room.isBooked() && !room.isOccupied()) {
            LocalDateTime unoccupiedSince = room.getUnoccupiedSince();
            
            // The room must have a time stamp for when it became unoccupied.
            if (unoccupiedSince != null) {
                long minutesUnoccupied = ChronoUnit.MINUTES.between(unoccupiedSince, currentTime);
                return minutesUnoccupied >= RELEASE_DURATION_MINUTES;
            }
        }
        return false;
    }
}