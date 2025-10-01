package com.example.services;

/**
 * Service to simulate user authentication for the Proxy Pattern.
 */
public class UserAuthenticator {
    
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "1234"; 

    /**
     * Checks if the provided credentials are valid for ADMIN access.
     */
    public boolean authenticateAdmin(String user, String pass) {
        return ADMIN_USER.equals(user) && ADMIN_PASS.equals(pass);
    }

    /**
     * Simulates basic USER authentication using a phone number.
     */
    public boolean authenticateUser(String phoneNumber) {
        // Simple validation: check if it's 10 digits
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }
}