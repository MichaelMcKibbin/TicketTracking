package com.tickettracking;

import java.time.LocalDateTime;

// Ticket.java
public class Ticket {
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String assignedTo;

    public enum Status {
        NEW, IN_PROGRESS, ON_HOLD, RESOLVED, CLOSED
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    // Constructor, getters, setters
}

