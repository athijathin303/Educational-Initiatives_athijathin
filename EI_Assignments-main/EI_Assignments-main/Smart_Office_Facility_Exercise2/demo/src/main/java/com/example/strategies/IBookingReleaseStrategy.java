package com.example.strategies;

import com.example.core.MeetingRoom;
import java.time.LocalDateTime;

/**
 * The Strategy interface (Behavioral Design Pattern).
 * Defines the contract for different booking release policies.
 */
public interface IBookingReleaseStrategy {

    /**
     * Checks if a room's booking should be automatically released based on the strategy.
     * @param room The meeting room to check.
     * @param currentTime The current time used for comparison.
     * @return true if the booking should be released, false otherwise.
     */
    boolean shouldRelease(MeetingRoom room, LocalDateTime currentTime);
}