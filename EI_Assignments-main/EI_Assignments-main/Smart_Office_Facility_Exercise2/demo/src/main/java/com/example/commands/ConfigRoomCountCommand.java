package com.example.commands;

import com.example.core.SmartOfficeHub;
import com.example.utilities.InvalidCommandArgumentException;

public class ConfigRoomCountCommand implements ICommand {

    private final SmartOfficeHub hub;
    private final int count;

    public ConfigRoomCountCommand(int count) {
        this.hub = SmartOfficeHub.getInstance();
        if (count <= 0) {
            throw new InvalidCommandArgumentException("Room count must be a positive number.");
        }
        this.count = count;
    }

    @Override
    public String execute() {
        return hub.configureRoomCount(count);
    }
}