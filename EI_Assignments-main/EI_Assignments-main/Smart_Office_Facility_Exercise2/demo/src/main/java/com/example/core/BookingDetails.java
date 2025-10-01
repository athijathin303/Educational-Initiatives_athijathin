package com.example.core;

import java.time.LocalTime;

/**
 * Represents a specific time slot booking for a meeting room.
 */
public class BookingDetails {
    private LocalTime startTime;
    private int durationMinutes; // Duration of the booking

    public BookingDetails(LocalTime startTime, int durationMinutes) {
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    // Helper method for time conflict validation
    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationMinutes);
    }

    @Override
    public String toString() {
        return String.format("%s for %d minutes", 
            startTime.toString(), durationMinutes);
    }
}