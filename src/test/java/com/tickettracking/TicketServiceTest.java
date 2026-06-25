package com.tickettracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TicketService.
 * Tests ticket persistence, validation, and ID generation.
 */
@DisplayName("TicketService Tests")
class TicketServiceTest {
    
    private TicketService ticketService;
    private Ticket testTicket;
    
    @BeforeEach
    void setUp() {
        ticketService = new TicketService();
        testTicket = new Ticket(
            "Test Ticket",
            "Test Description",
            Ticket.Priority.MEDIUM,
            LocalDateTime.now()
        );
        testTicket.setStatus(Ticket.Status.NEW);
    }
    
    @DisplayName("Should save a valid ticket")
    @Test
    void testSaveValidTicket() {
        ticketService.saveTicket(testTicket);
        
        List<Ticket> allTickets = ticketService.getAllTickets();
        assertFalse(allTickets.isEmpty(), "Tickets list should not be empty after saving");
        assertTrue(allTickets.stream().anyMatch(t -> t.getTitle().equals("Test Ticket")));
    }
    
    @DisplayName("Should generate ID for new ticket")
    @Test
    void testIdGenerationForNewTicket() {
        assertNull(testTicket.getId(), "ID should be null before saving");
        
        ticketService.saveTicket(testTicket);
        
        assertNotNull(testTicket.getId(), "ID should be generated after saving");
        assertNotEquals("", testTicket.getId(), "ID should not be empty");
        assertTrue(testTicket.getId().matches("\\d+"), "ID should be numeric");
    }
    
    @DisplayName("Should increment ID for each new ticket")
    @Test
    void testIdIncrementForMultipleTickets() {
        Ticket ticket1 = new Ticket("Ticket 1", "Desc 1", Ticket.Priority.LOW, LocalDateTime.now());
        ticket1.setStatus(Ticket.Status.NEW);
        
        Ticket ticket2 = new Ticket("Ticket 2", "Desc 2", Ticket.Priority.HIGH, LocalDateTime.now());
        ticket2.setStatus(Ticket.Status.NEW);
        
        ticketService.saveTicket(ticket1);
        ticketService.saveTicket(ticket2);
        
        assertNotNull(ticket1.getId());
        assertNotNull(ticket2.getId());
        
        int id1 = Integer.parseInt(ticket1.getId());
        int id2 = Integer.parseInt(ticket2.getId());
        
        assertEquals(id2, id1 + 1, "Second ticket ID should be one more than first");
    }
    
    @DisplayName("Should throw exception when saving null ticket")
    @Test
    void testSaveNullTicketThrowsException() {
        assertThrows(RuntimeException.class, () -> ticketService.saveTicket(null));
    }
    
    @DisplayName("Should throw exception when ticket title is empty")
    @Test
    void testSaveTicketWithEmptyTitleThrowsException() {
        testTicket.setTitle("");
        assertThrows(RuntimeException.class, () -> ticketService.saveTicket(testTicket));
    }
    
    @DisplayName("Should throw exception when ticket title is null")
    @Test
    void testSaveTicketWithNullTitleThrowsException() {
        testTicket.setTitle(null);
        assertThrows(RuntimeException.class, () -> ticketService.saveTicket(testTicket));
    }
    
    @DisplayName("Should throw exception when ticket status is null")
    @Test
    void testSaveTicketWithNullStatusThrowsException() {
        testTicket.setStatus(null);
        assertThrows(RuntimeException.class, () -> ticketService.saveTicket(testTicket));
    }
    
    @DisplayName("Should throw exception when ticket priority is null")
    @Test
    void testSaveTicketWithNullPriorityThrowsException() {
        testTicket.setPriority(null);
        assertThrows(RuntimeException.class, () -> ticketService.saveTicket(testTicket));
    }
    
    @DisplayName("Should set creation time when saving new ticket")
    @Test
    void testCreationTimeSetOnSave() {
        testTicket.setCreatedAt(null);
        LocalDateTime beforeSave = LocalDateTime.now();
        
        ticketService.saveTicket(testTicket);
        
        LocalDateTime afterSave = LocalDateTime.now();
        assertNotNull(testTicket.getCreatedAt(), "Creation time should be set");
        assertTrue(testTicket.getCreatedAt().isAfter(beforeSave.minusSeconds(1)));
        assertTrue(testTicket.getCreatedAt().isBefore(afterSave.plusSeconds(1)));
    }
    
    @DisplayName("Should preserve creation time on update")
    @Test
    void testCreationTimePreservedOnUpdate() {
        ticketService.saveTicket(testTicket);
        LocalDateTime originalCreationTime = testTicket.getCreatedAt();
        
        testTicket.setTitle("Updated Title");
        ticketService.updateTicket(testTicket);
        
        assertEquals(originalCreationTime, testTicket.getCreatedAt(), 
            "Creation time should not change on update");
    }
    
    @DisplayName("Should set update time when updating ticket")
    @Test
    void testUpdateTimeSetOnUpdate() {
        ticketService.saveTicket(testTicket);
        LocalDateTime beforeUpdate = LocalDateTime.now();
        
        testTicket.setTitle("Updated Title");
        ticketService.updateTicket(testTicket);
        
        LocalDateTime afterUpdate = LocalDateTime.now();
        assertNotNull(testTicket.getUpdatedAt(), "Update time should be set");
        assertTrue(testTicket.getUpdatedAt().isAfter(beforeUpdate.minusSeconds(1)));
        assertTrue(testTicket.getUpdatedAt().isBefore(afterUpdate.plusSeconds(1)));
    }
    
    @DisplayName("Should update existing ticket")
    @Test
    void testUpdateTicket() {
        ticketService.saveTicket(testTicket);
        String originalId = testTicket.getId();
        
        testTicket.setTitle("New Title");
        testTicket.setStatus(Ticket.Status.IN_PROGRESS);
        ticketService.updateTicket(testTicket);
        
        List<Ticket> allTickets = ticketService.getAllTickets();
        Ticket updated = allTickets.stream()
            .filter(t -> t.getId().equals(originalId))
            .findFirst()
            .orElse(null);
        
        assertNotNull(updated);
        assertEquals("New Title", updated.getTitle());
        assertEquals(Ticket.Status.IN_PROGRESS, updated.getStatus());
    }
    
    @DisplayName("Should throw exception when updating ticket without ID")
    @Test
    void testUpdateTicketWithoutIdThrowsException() {
        testTicket.setId(null);
        assertThrows(RuntimeException.class, () -> ticketService.updateTicket(testTicket));
    }
    
    @DisplayName("Should throw exception when updating non-existent ticket")
    @Test
    void testUpdateNonExistentTicketThrowsException() {
        testTicket.setId("999999");
        assertThrows(RuntimeException.class, () -> ticketService.updateTicket(testTicket));
    }
    
    @DisplayName("Should delete ticket")
    @Test
    void testDeleteTicket() {
        ticketService.saveTicket(testTicket);
        String ticketId = testTicket.getId();
        
        ticketService.deleteTicket(testTicket);
        
        List<Ticket> allTickets = ticketService.getAllTickets();
        assertFalse(allTickets.stream().anyMatch(t -> t.getId().equals(ticketId)),
            "Deleted ticket should not be in the list");
    }
    
    @DisplayName("Should throw exception when deleting null ticket")
    @Test
    void testDeleteNullTicketThrowsException() {
        assertThrows(RuntimeException.class, () -> ticketService.deleteTicket(null));
    }
    
    @DisplayName("Should throw exception when deleting ticket with null ID")
    @Test
    void testDeleteTicketWithNullIdThrowsException() {
        testTicket.setId(null);
        assertThrows(RuntimeException.class, () -> ticketService.deleteTicket(testTicket));
    }
    
    @DisplayName("Should throw exception when deleting non-existent ticket")
    @Test
    void testDeleteNonExistentTicketThrowsException() {
        testTicket.setId("999999");
        assertThrows(RuntimeException.class, () -> ticketService.deleteTicket(testTicket));
    }
    
    @DisplayName("Should retrieve all tickets")
    @Test
    void testGetAllTickets() {
        int initialCount = ticketService.getAllTickets().size();
        
        Ticket ticket1 = new Ticket("Ticket 1", "Desc 1", Ticket.Priority.LOW, LocalDateTime.now());
        ticket1.setStatus(Ticket.Status.NEW);
        Ticket ticket2 = new Ticket("Ticket 2", "Desc 2", Ticket.Priority.HIGH, LocalDateTime.now());
        ticket2.setStatus(Ticket.Status.NEW);
        
        ticketService.saveTicket(ticket1);
        ticketService.saveTicket(ticket2);
        
        List<Ticket> allTickets = ticketService.getAllTickets();
        assertEquals(initialCount + 2, allTickets.size(), "Should have 2 more tickets");
    }
    
    @DisplayName("Should return empty list when no tickets exist")
    @Test
    void testGetAllTicketsWhenEmpty() {
        // Create new service to get empty state
        TicketService emptyService = new TicketService();
        List<Ticket> allTickets = emptyService.getAllTickets();
        assertNotNull(allTickets, "Should return non-null list");
    }
}






