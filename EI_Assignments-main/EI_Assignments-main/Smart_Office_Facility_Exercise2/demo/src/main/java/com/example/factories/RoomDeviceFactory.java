package com.example.factories;
import com.example.controllers.ACController;
import com.example.controllers.LightController;
import com.example.core.MeetingRoom;
import com.example.controllers.NotificationService;

/**
 * Factory Method Pattern (Integrated from Exercise 1).
 * Used to create and assign devices (IController observers) to a MeetingRoom.
 * This simplifies adding new device types (e.g., ProjectorController) later.
 */

public class RoomDeviceFactory {
    public static void equipRoom(MeetingRoom room) {
        room.addController(new LightController(room.getRoomId()));
        room.addController(new ACController(room.getRoomId()));
        room.addController(new NotificationService());
    }
}