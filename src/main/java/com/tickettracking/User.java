package com.tickettracking;

// User.java
public class User {
    private String id;
    private String username;
    private String password; // Will need proper encryption
    private Role role;

    public enum Role {
        ADMIN, SUPPORT_STAFF, CUSTOMER
    }

    // Constructor, getters, setters
}
