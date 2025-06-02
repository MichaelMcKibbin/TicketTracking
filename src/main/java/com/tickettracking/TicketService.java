package com.tickettracking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class TicketService {
    private static final String TICKET_FILE = "tickets.json";
    private ObjectMapper objectMapper;

    public TicketService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<com.tickettracking.Ticket> loadTickets() {
        try {
            File file = new File(TICKET_FILE);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file,
                new TypeReference<List<com.tickettracking.Ticket>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveTickets(List<com.tickettracking.Ticket> tickets) {
        try {
            objectMapper.writeValue(new File(TICKET_FILE), tickets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

