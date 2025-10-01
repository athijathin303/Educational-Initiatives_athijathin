package com.example.commands;

public class ExitCommand implements ICommand {
    @Override
    public String execute() {
        return "Application shutting down. Goodbye!";
    }
}