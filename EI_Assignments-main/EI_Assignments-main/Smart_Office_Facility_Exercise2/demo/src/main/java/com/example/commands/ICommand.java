package com.example.commands;

import com.example.utilities.CommandExecutionException;

public interface ICommand {
    
    /**
     * Executes the specific action encapsulated by the command.
     * @return The message to be displayed to the console upon successful execution.
     * @throws CommandExecutionException for any business rule or validation error.
     */
    String execute();
}