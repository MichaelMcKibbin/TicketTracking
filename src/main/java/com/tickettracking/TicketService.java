package com.tickettracking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class TicketService {
    private static final String DATA_DIR = "data";
    private static final String TICKET_FILE = "tickets.json";
    private Path ticketFilePath;
    private ObjectMapper objectMapper;

    public TicketService() {
        // Initialize ObjectMapper
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Setup data directory and file path
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            ticketFilePath = Paths.get(DATA_DIR, TICKET_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory", e);
        }
    }

    // Method to save tickets using ObjectMapper
    public void saveTickets(List<Ticket> tickets) {
        try {
            objectMapper.writeValue(ticketFilePath.toFile(), tickets);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save tickets", e);
        }
    }

    // Method to load tickets using ObjectMapper
    public List<Ticket> loadTickets() {
        if (!Files.exists(ticketFilePath)) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }
        try {
            return objectMapper.readValue(
                    ticketFilePath.toFile(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Ticket.class)
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tickets", e);
        }
    }
//    public TicketService() {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//    }
//
//    public List<com.tickettracking.Ticket> loadTickets() {
//        try {
//            File file = new File(TICKET_FILE);
//            if (!file.exists()) {
//                return new ArrayList<>();
//            }
//            return objectMapper.readValue(file,
//                new TypeReference<List<com.tickettracking.Ticket>>() {});
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//
//    public void saveTickets(List<com.tickettracking.Ticket> tickets) {
//        try {
//            objectMapper.writeValue(new File(TICKET_FILE), tickets);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

