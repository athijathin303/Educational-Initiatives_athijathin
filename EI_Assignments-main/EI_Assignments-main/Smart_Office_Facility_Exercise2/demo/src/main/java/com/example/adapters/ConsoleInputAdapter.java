package com.example.adapters;

import com.example.commands.*;
import com.example.core.Session;
import com.example.proxies.AuthenticatedCommandProxy;
import com.example.utilities.CommandExecutionException;
import com.example.utilities.InvalidCommandArgumentException;
import com.example.utilities.Logger;

public class ConsoleInputAdapter {
    
    private final Logger logger = Logger.getInstance();
    private final Session session; // Reference to the current session

    public ConsoleInputAdapter(Session session) {
        this.session = session;
    }

    public ICommand parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidCommandArgumentException("Input cannot be empty.");
        }
        
        String[] parts = input.trim().split("\\s+");
        String commandType = parts[0].toLowerCase();
        
        // Check if the command is one of the secured types
        if (commandType.equals("config") || commandType.equals("block") || commandType.equals("cancel")) {
            return parseSecuredCommand(parts);
        }
        
        // Process unsecured commands directly
        return parseUnsecuredCommand(parts, commandType);
    }


    private ICommand parseSecuredCommand(String[] parts) {
        // 1. Check Authentication Status
        if (!session.isAuthenticated()) {
             throw new CommandExecutionException("Access denied. Please login as admin to execute this command.");
        }
       
        ICommand realCommand = parseRealCommand(parts);
        
        return new AuthenticatedCommandProxy(realCommand, session.getUsername(), session.getPassword());
    }

    private ICommand parseUnsecuredCommand(String[] parts, String commandType) {
        try {
            return switch (commandType) {
                case "add" -> parseAddOccupantCommand(parts);
                case "stats" -> new GenerateStatsCommand();
                case "exit" -> new ExitCommand();
                default -> throw new InvalidCommandArgumentException("Unknown command: " + commandType);
            };
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("Invalid number format provided.");
        }
    }

    private ICommand parseRealCommand(String[] parts) {
        String commandType = parts[0].toLowerCase();

        try {
            return switch (commandType) {
                case "config" -> parseConfigCommand(parts);
                case "block" -> parseBlockCommand(parts);
                case "cancel" -> parseCancelCommand(parts);
                default -> throw new InvalidCommandArgumentException("Unknown command: " + commandType);
            };
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentException("Invalid number format provided.");
        }
    }

    private ICommand parseConfigCommand(String[] parts) {
        // Expected: config room count 3
        if (parts.length < 4 || !parts[1].equals("room") || !parts[2].equals("count")) {
            throw new InvalidCommandArgumentException("Usage: Config room count [number]");
        }
        int count = Integer.parseInt(parts[3]);
        return new ConfigRoomCountCommand(count);
    }

    private ICommand parseBlockCommand(String[] parts) {
        // Expected: block room 1 09:00 60
        if (parts.length < 5 || !parts[1].equals("room")) {
            throw new InvalidCommandArgumentException("Usage: Block room [id] [time HH:mm] [duration minutes]");
        }
        int roomId = Integer.parseInt(parts[2]);
        String startTime = parts[3];
        int duration = Integer.parseInt(parts[4]);
        return new BookRoomCommand(roomId, startTime, duration);
    }
    
    private ICommand parseCancelCommand(String[] parts) {
        // Expected: cancel room 1
        if (parts.length < 3 || !parts[1].equals("room")) {
            throw new InvalidCommandArgumentException("Usage: Cancel room [id]");
        }
        int roomId = Integer.parseInt(parts[2]);
        return new CancelBookingCommand(roomId);
    }

    private ICommand parseAddOccupantCommand(String[] parts) {
        // Expected: add occupant 1 2
        if (parts.length < 4 || !parts[1].equals("occupant")) {
            throw new InvalidCommandArgumentException("Usage: Add occupant [room id] [count]");
        }
        int roomId = Integer.parseInt(parts[2]);
        int count = Integer.parseInt(parts[3]);
        return new AddOccupantCommand(roomId, count);
    }
}