package com.tickettracking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * The TicketService class provides functionalities to manage Ticket objects.
 * It handles CRUD (Create, Read, Update, Delete) operations and persists tickets
 * in a JSON file for storage.
 */
public class TicketService {
    private static final String FILE_PATH = "src/main/resources/tickets/tickets.json";
    private final ObjectMapper objectMapper;
    private List<Ticket> tickets; // Cache the tickets in memory

    public TicketService() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.tickets = loadTicketsFromFile();
    }

    private List<Ticket> loadTicketsFromFile() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }
            String content = Files.readString(file.toPath());
            if (content == null || content.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Error reading tickets from file", e);
            return new ArrayList<>();
        }
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public void saveTicket(Ticket ticket) {
        // Validation throws IllegalArgumentException directly — no logging needed
        validateTicket(ticket);

        // Set creation time for new ticket
        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }

        // Generate new ID for new ticket
        if (ticket.getId() == null || ticket.getId().isEmpty()) {
            // Find the maximum numeric ID
            int maxId = tickets.stream()
                .map(Ticket::getId)
                .filter(id -> id != null && !id.isEmpty())
                .mapToInt(id -> {
                    try {
                        return Integer.parseInt(id);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
            ticket.setId(String.valueOf(maxId + 1));
        }

        tickets.add(ticket);
        try {
            saveAllTickets(tickets);
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Error saving ticket to file", e);
            throw new RuntimeException("Failed to save ticket", e);
        }
    }

    public void updateTicket(Ticket editedTicket) {
        // Validation throws IllegalArgumentException directly — no logging needed
        validateTicket(editedTicket);
        if (editedTicket.getId() == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null for update");
        }

        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getId().equals(editedTicket.getId())) {
                editedTicket.setUpdatedAt(LocalDateTime.now());
                tickets.set(i, editedTicket);
                try {
                    saveAllTickets(tickets);
                } catch (IOException e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                            "Error saving updated ticket to file", e);
                    throw new RuntimeException("Failed to update ticket", e);
                }
                return;
            }
        }
        throw new RuntimeException("Ticket not found with ID: " + editedTicket.getId());
    }

    public void deleteTicket(Ticket ticket) {
        // Validation throws IllegalArgumentException directly — no logging needed
        if (ticket == null || ticket.getId() == null) {
            throw new IllegalArgumentException("Ticket or ticket ID cannot be null");
        }

        boolean removed = tickets.removeIf(t ->
            t.getId() != null && t.getId().equals(ticket.getId()));

        if (removed) {
            try {
                saveAllTickets(tickets);
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "Error saving tickets after deletion to file", e);
                throw new RuntimeException("Failed to delete ticket", e);
            }
        } else {
            throw new RuntimeException("Ticket not found with ID: " + ticket.getId());
        }
    }

    private void validateTicket(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket cannot be null");
        }
        if (ticket.getTitle() == null || ticket.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket title is required");
        }
        if (ticket.getStatus() == null) {
            throw new IllegalArgumentException("Ticket status is required");
        }
        if (ticket.getPriority() == null) {
            throw new IllegalArgumentException("Ticket priority is required");
        }
    }
    
    private void saveAllTickets(List<Ticket> tickets) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(FILE_PATH), tickets);
    }
}
