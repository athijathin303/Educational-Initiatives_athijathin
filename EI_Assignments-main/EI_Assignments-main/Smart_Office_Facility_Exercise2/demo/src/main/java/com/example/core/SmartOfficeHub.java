package com.example.core;

import com.example.factories.RoomDeviceFactory;
import com.example.utilities.Logger;
import com.example.utilities.RoomNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SmartOfficeHub {
    
    private static SmartOfficeHub instance;
    private final Logger logger;
    
    private final Map<Integer, MeetingRoom> rooms; // Store rooms by ID

    // Private constructor prevents external instantiation (Singleton)
    private SmartOfficeHub() {
        this.logger = Logger.getInstance();
        this.rooms = new HashMap<>();
    }

    // Static method to get the single instance
    public static SmartOfficeHub getInstance() {
        if (instance == null) {
            synchronized (SmartOfficeHub.class) {
                if (instance == null) {
                    instance = new SmartOfficeHub();
                }
            }
        }
        return instance;
    }

    // --- Configuration (Mandatory Requirement 1) ---

    public String configureRoomCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Room count must be a positive number.");
        }
        
        int oldSize = rooms.size();
        rooms.clear();
        StringBuilder roomList = new StringBuilder();
        
        for (int i = 1; i <= count; i++) {
        MeetingRoom room = new MeetingRoom(i);
        // NEW: Use the Factory Method to equip the room with controllers
        RoomDeviceFactory.equipRoom(room); 
        
        rooms.put(i, room);
        roomList.append("Room ").append(i).append(i == count ? "." : ", ");
    }
        
        logger.logInfo(String.format("Configured office from %d to %d rooms.", oldSize, count));
        return String.format("Office configured with %d meeting rooms: %s", count, roomList.toString());
    }
    
    // --- Room Accessors ---

    public MeetingRoom getRoom(int roomId) {
        if (!rooms.containsKey(roomId)) {
            throw new RoomNotFoundException(roomId);
        }
        return rooms.get(roomId);
    }
    
    public Map<Integer, MeetingRoom> getAllRooms() {
        return Collections.unmodifiableMap(rooms);
    }
    
    // --- Other methods (e.g., for optional statistics) will be added here later ---
}