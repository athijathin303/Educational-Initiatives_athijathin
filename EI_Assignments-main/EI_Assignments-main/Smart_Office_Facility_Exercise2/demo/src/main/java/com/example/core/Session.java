package com.example.core;

/**
 * Tracks the current session state for the user.
 * Not a Singleton, as it tracks the current user's state.
 */
public class Session {
    private String username;
    private String password;
    private boolean isAuthenticated = false;

    public void loginAdmin(String user, String pass) {
        this.username = user;
        this.password = pass;
        this.isAuthenticated = true;
    }

    public void logout() {
        this.username = null;
        this.password = null;
        this.isAuthenticated = false;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}