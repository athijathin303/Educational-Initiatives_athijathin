package com.example.controllers;

import com.example.core.MeetingRoom;

public interface IController {
    /**
     * Called by the MeetingRoom (Subject) when its state changes.
     * @param room The room that changed state.
     */
    void update(MeetingRoom room);
}