package com.example.core;

import com.example.controllers.IController;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single meeting room. Acts as the Subject in the Observer Pattern.
 */
public class MeetingRoom {
    private final int roomId;
    private int maxCapacity = 0; // Default until configured
    private int currentOccupancy = 0;

    private BookingDetails currentBooking;
    private boolean isOccupied = false;
    private LocalDateTime unoccupiedSince; // Used for Req 4 (5-minute release)
    private int timesBooked = 0; 

    // List of Observers (Controllers)
    private final List<IController> controllers = new ArrayList<>();

    public MeetingRoom(int roomId) {
        this.roomId = roomId;
    }

    // --- Observer Pattern Implementation ---

    public void addController(IController controller) {
        this.controllers.add(controller);
    }

    private void notifyControllers() {
        // Notifies all subscribed controllers (Lights, AC) of the state change
        for (IController controller : controllers) {
            controller.update(this);
        }
    }

    public int getTimesBooked() {
    return timesBooked;
    }

    // --- State Management ---

    /**
     * Updates occupancy and triggers automation (Observer Pattern).
     * @param count The new occupancy count.
     * @return Output string.
     */
    public String setOccupancy(int count) {
        this.currentOccupancy = count;
        boolean wasOccupied = this.isOccupied;
        
        // Mandatory Requirement 3: Occupancy detected when at least two people enter
        this.isOccupied = (count >= 2);
        
        String output;

        if (this.isOccupied) {
            // Reset the 5-minute timer
            this.unoccupiedSince = null; 
            output = String.format("Room %d is now occupied by %d persons. AC and lights turned on.", roomId, count);
        } else {
            // Mandatory Requirement 5 logic triggered via Observer
            if (count > 0) {
                 output = String.format("Room %d occupancy insufficient (%d) to mark as fully occupied.", roomId, count);
            } else {
                 output = String.format("Room %d is now unoccupied. AC and lights turned off.", roomId);
            }
            // Start the 5-minute timer only if it was previously occupied, or if it has a booking
            if (wasOccupied || currentBooking != null) {
                 this.unoccupiedSince = LocalDateTime.now(); 
            }
        }
        
        // Notify devices of the state change
        notifyControllers();
        return output;
    }
    
    // --- Booking Methods ---
    
    public void setBooking(BookingDetails booking) {
        this.currentBooking = booking;
        this.timesBooked++; 
    }
    
    public void releaseBooking() {
        this.currentBooking = null;
        // The room should be marked unoccupied to trigger AC/Light turn-off via Monitor Service
        if (this.currentOccupancy < 2) {
             this.isOccupied = false;
             notifyControllers(); // Notify controllers after releasing the booking
        }
        // Note: The monitor service handles the console output for release
    }

    // --- Getters ---
    
    public int getRoomId() { return roomId; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentOccupancy() { return currentOccupancy; }
    public BookingDetails getCurrentBooking() { return currentBooking; }
    public boolean isBooked() { return currentBooking != null; }
    public boolean isOccupied() { return isOccupied; }
    public LocalDateTime getUnoccupiedSince() { return unoccupiedSince; }

    public void setMaxCapacity(int capacity) {
        this.maxCapacity = capacity;
    }
    
    // Simple conflict check
    public boolean checkConflict(LocalTime newStart, LocalTime newEnd) {
        if (!isBooked()) return false;
        
        LocalTime existingStart = currentBooking.getStartTime();
        LocalTime existingEnd = currentBooking.getEndTime();
        
        // Check for overlap: [Start A, End A] overlaps with [Start B, End B]
        return !newEnd.isBefore(existingStart) && !newStart.isAfter(existingEnd);
    }

    @Override
    public String toString() {
        String bookingStatus = currentBooking != null ? "BOOKED " + currentBooking.toString() : "FREE";
        String occupancyStatus = isOccupied ? "OCCUPIED" : "UNOCCUPIED";
        
        return String.format("Room %d [Cap: %d] | Status: %s | Occupancy: %d | Booking: %s", 
            roomId, maxCapacity, occupancyStatus, currentOccupancy, bookingStatus);
    }
}