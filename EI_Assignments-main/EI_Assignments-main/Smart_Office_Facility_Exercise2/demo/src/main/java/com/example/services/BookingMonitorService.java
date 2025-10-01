package com.example.services;

import com.example.core.SmartOfficeHub;
import com.example.strategies.IBookingReleaseStrategy;
import com.example.utilities.Logger;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Dedicated background service to monitor rooms and automatically release bookings.
 * Implements Mandatory Requirement 4 using the Strategy Pattern.
 */
public class BookingMonitorService {

    private final SmartOfficeHub hub;
    private final IBookingReleaseStrategy releaseStrategy;
    private final Logger logger = Logger.getInstance();
    private final ScheduledExecutorService scheduler;

    // Check rooms every 60 seconds (1 minute) in the background
    private static final int CHECK_INTERVAL_SECONDS = 60; 

    public BookingMonitorService(IBookingReleaseStrategy strategy) {
        this.hub = SmartOfficeHub.getInstance();
        this.releaseStrategy = strategy;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Starts the periodic monitoring task.
     */
    public void startMonitoring() {
        // Schedule the monitoring task to run every CHECK_INTERVAL_SECONDS
        scheduler.scheduleAtFixedRate(this::monitorBookings, 0, CHECK_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * The core task that checks all rooms against the release strategy.
     */
    private void monitorBookings() {
        LocalDateTime currentTime = LocalDateTime.now();
        // logger.logInfo("Running background booking monitor..."); 

        // Iterate over all rooms managed by the Singleton hub
        for (var roomEntry : hub.getAllRooms().entrySet()) {
            var room = roomEntry.getValue();
            
            if (releaseStrategy.shouldRelease(room, currentTime)) {
                
                // 1. Release the booking
                room.releaseBooking(); 
                
                // 2. Log the action
                logger.logInfo(String.format("Room %d: Booking auto-released (5 min timeout).", room.getRoomId()));
                
                // 3. Provide console output as specified in the problem statement
                System.out.println(
                    String.format("Room %d is now unoccupied. Booking released. AC and lights off.", room.getRoomId()));
            }
        }
    }
    
    /**
     * Shuts down the background service cleanly.
     */
    public void stopMonitoring() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdownNow();
            logger.logInfo("BookingMonitorService stopped.");
        }
    }
}