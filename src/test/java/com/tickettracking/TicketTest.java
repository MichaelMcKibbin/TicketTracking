package com.tickettracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Ticket.
 * Tests comment handling, default values, and ticket functionality.
 */
@DisplayName("Ticket Tests")
class TicketTest {
    
    private Ticket ticket;
    
    @BeforeEach
    void setUp() {
        ticket = new Ticket();
    }
    
    @DisplayName("Should have default values in default constructor")
    @Test
    void testDefaultConstructorValues() {
        assertEquals("", ticket.getTitle(), "Title should default to empty string");
        assertEquals("", ticket.getDescription(), "Description should default to empty string");
        assertEquals(Ticket.Priority.LOW, ticket.getPriority(), "Priority should default to LOW");
        assertNotNull(ticket.getComments(), "Comments should not be null");
        assertTrue(ticket.getComments().isEmpty(), "Comments should be empty");
    }
    
    @DisplayName("Should initialize with constructor parameters")
    @Test
    void testParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = new Ticket("Test Title", "Test Description", 
            Ticket.Priority.HIGH, now);
        
        assertEquals("Test Title", ticket.getTitle());
        assertEquals("Test Description", ticket.getDescription());
        assertEquals(Ticket.Priority.HIGH, ticket.getPriority());
        assertEquals(Ticket.Status.NEW, ticket.getStatus());
        assertEquals(now, ticket.getCreatedAt());
        assertEquals(now, ticket.getUpdatedAt());
        assertNull(ticket.getAssignedTo(), "Should be initially unassigned");
    }
    
    @DisplayName("Should add comment to ticket")
    @Test
    void testAddComment() {
        Comment comment = new Comment("Test comment", "user1");
        ticket.setId("1");
        
        ticket.addComment(comment);
        
        assertEquals(1, ticket.getComments().size(), "Should have one comment");
        assertEquals("Test comment", ticket.getComments().get(0).getContent());
        assertEquals("user1", ticket.getComments().get(0).getCreatedBy());
    }
    
    @DisplayName("Should set ticket ID on added comment")
    @Test
    void testCommentReceivesTicketId() {
        ticket.setId("123");
        Comment comment = new Comment("Test comment", "user1");
        
        ticket.addComment(comment);
        
        assertEquals("123", comment.getTicketId(), "Comment should have ticket ID set");
    }
    
    @DisplayName("Should add multiple comments")
    @Test
    void testAddMultipleComments() {
        ticket.setId("1");
        Comment comment1 = new Comment("Comment 1", "user1");
        Comment comment2 = new Comment("Comment 2", "user2");
        
        ticket.addComment(comment1);
        ticket.addComment(comment2);
        
        assertEquals(2, ticket.getComments().size());
        assertEquals("Comment 1", ticket.getComments().get(0).getContent());
        assertEquals("Comment 2", ticket.getComments().get(1).getContent());
    }
    
    @DisplayName("Should initialize comments list if null on add")
    @Test
    void testCommentListInitializedIfNull() {
        ticket.setId("1");
        ticket.setComments(null);
        Comment comment = new Comment("Test", "user1");
        
        ticket.addComment(comment);
        
        assertNotNull(ticket.getComments(), "Comments list should be initialized");
        assertEquals(1, ticket.getComments().size());
    }
    
    @DisplayName("Should return empty list for null comments")
    @Test
    void testGetCommentsReturnsEmptyListIfNull() {
        ticket.setComments(null);
        
        List<Comment> comments = ticket.getComments();
        
        assertNotNull(comments, "Should not return null");
        assertTrue(comments.isEmpty(), "Should return empty list");
    }
    
    @DisplayName("Should set and get all fields")
    @Test
    void testSettersAndGetters() {
        ticket.setId("1");
        ticket.setTitle("New Title");
        ticket.setDescription("New Description");
        ticket.setStatus(Ticket.Status.RESOLVED);
        ticket.setPriority(Ticket.Priority.CRITICAL);
        ticket.setAssignedTo("user1");
        
        LocalDateTime now = LocalDateTime.now();
        ticket.setCreatedAt(now);
        ticket.setUpdatedAt(now);
        
        assertEquals("1", ticket.getId());
        assertEquals("New Title", ticket.getTitle());
        assertEquals("New Description", ticket.getDescription());
        assertEquals(Ticket.Status.RESOLVED, ticket.getStatus());
        assertEquals(Ticket.Priority.CRITICAL, ticket.getPriority());
        assertEquals("user1", ticket.getAssignedTo());
        assertEquals(now, ticket.getCreatedAt());
        assertEquals(now, ticket.getUpdatedAt());
    }
    
    @DisplayName("Should support all Status enum values")
    @Test
    void testAllStatusValues() {
        Ticket.Status[] statuses = Ticket.Status.values();
        
        for (Ticket.Status status : statuses) {
            ticket.setStatus(status);
            assertEquals(status, ticket.getStatus());
        }
        
        assertEquals(6, statuses.length, "Should have 6 status values");
    }
    
    @DisplayName("Should support all Priority enum values")
    @Test
    void testAllPriorityValues() {
        Ticket.Priority[] priorities = Ticket.Priority.values();
        
        for (Ticket.Priority priority : priorities) {
            ticket.setPriority(priority);
            assertEquals(priority, ticket.getPriority());
        }
        
        assertEquals(4, priorities.length, "Should have 4 priority values");
    }
    
    @DisplayName("Should handle null assigned user")
    @Test
    void testNullAssignedUser() {
        ticket.setAssignedTo(null);
        assertNull(ticket.getAssignedTo());
    }
    
    @DisplayName("Should maintain comments list consistency")
    @Test
    void testCommentListConsistency() {
        ticket.setId("1");
        
        // Add comments
        ticket.addComment(new Comment("Comment 1", "user1"));
        ticket.addComment(new Comment("Comment 2", "user2"));
        
        List<Comment> comments1 = ticket.getComments();
        List<Comment> comments2 = ticket.getComments();
        
        // Should get the same list
        assertEquals(comments1.size(), comments2.size());
    }
}

