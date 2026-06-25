package com.tickettracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Comment.
 * Tests comment creation, content, authorship, and timestamp handling.
 */
@DisplayName("Comment Tests")
class CommentTest {
    
    private Comment comment;
    
    @BeforeEach
    void setUp() {
        comment = new Comment();
    }
    
    @DisplayName("Should create comment with default constructor")
    @Test
    void testDefaultConstructor() {
        Comment newComment = new Comment();
        
        assertNull(newComment.getId());
        assertNull(newComment.getTicketId());
        assertNull(newComment.getContent());
        assertNull(newComment.getCreatedBy());
        assertNull(newComment.getCreatedAt());
    }
    
    @DisplayName("Should create comment with content and author")
    @Test
    void testParameterizedConstructor() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        Comment newComment = new Comment("Test comment", "user1");
        LocalDateTime afterCreation = LocalDateTime.now();
        
        assertEquals("Test comment", newComment.getContent());
        assertEquals("user1", newComment.getCreatedBy());
        assertNotNull(newComment.getCreatedAt());
        assertTrue(newComment.getCreatedAt().isAfter(beforeCreation.minusSeconds(1)));
        assertTrue(newComment.getCreatedAt().isBefore(afterCreation.plusSeconds(1)));
    }
    
    @DisplayName("Should set and get ID")
    @Test
    void testSetGetId() {
        comment.setId("comment123");
        
        assertEquals("comment123", comment.getId());
    }
    
    @DisplayName("Should set and get ticket ID")
    @Test
    void testSetGetTicketId() {
        comment.setTicketId("ticket456");
        
        assertEquals("ticket456", comment.getTicketId());
    }
    
    @DisplayName("Should set and get content")
    @Test
    void testSetGetContent() {
        String content = "This is a detailed comment about the ticket.";
        comment.setContent(content);
        
        assertEquals(content, comment.getContent());
    }
    
    @DisplayName("Should set and get created by (author)")
    @Test
    void testSetGetCreatedBy() {
        comment.setCreatedBy("john_doe");
        
        assertEquals("john_doe", comment.getCreatedBy());
    }
    
    @DisplayName("Should set and get created at (timestamp)")
    @Test
    void testSetGetCreatedAt() {
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        comment.setCreatedAt(timestamp);
        
        assertEquals(timestamp, comment.getCreatedAt());
    }
    
    @DisplayName("Should handle long comment content")
    @Test
    void testLongCommentContent() {
        String longContent = "A".repeat(1000);
        comment.setContent(longContent);
        
        assertEquals(longContent, comment.getContent());
        assertEquals(1000, comment.getContent().length());
    }
    
    @DisplayName("Should handle special characters in content")
    @Test
    void testSpecialCharactersInContent() {
        String content = "Comment with special chars: !@#$%^&*()_+-=[]{}|;:'\",.<>?/\\";
        comment.setContent(content);
        
        assertEquals(content, comment.getContent());
    }
    
    @DisplayName("Should handle special characters in author name")
    @Test
    void testSpecialCharactersInAuthor() {
        comment.setCreatedBy("user@example.com");
        
        assertEquals("user@example.com", comment.getCreatedBy());
    }
    
    @DisplayName("Should handle empty content")
    @Test
    void testEmptyContent() {
        comment.setContent("");
        
        assertEquals("", comment.getContent());
    }
    
    @DisplayName("Should set all fields independently")
    @Test
    void testIndependentFieldSetting() {
        LocalDateTime timestamp = LocalDateTime.now();
        
        comment.setId("1");
        comment.setTicketId("100");
        comment.setContent("Comment text");
        comment.setCreatedBy("user1");
        comment.setCreatedAt(timestamp);
        
        assertEquals("1", comment.getId());
        assertEquals("100", comment.getTicketId());
        assertEquals("Comment text", comment.getContent());
        assertEquals("user1", comment.getCreatedBy());
        assertEquals(timestamp, comment.getCreatedAt());
        
        // Modify one field
        comment.setContent("Updated comment");
        
        // Others should remain unchanged
        assertEquals("1", comment.getId());
        assertEquals("100", comment.getTicketId());
        assertEquals("Updated comment", comment.getContent());
        assertEquals("user1", comment.getCreatedBy());
        assertEquals(timestamp, comment.getCreatedAt());
    }
    
    @DisplayName("Should preserve timestamp when set via constructor")
    @Test
    void testTimestampPreservationInConstructor() {
        Comment comment1 = new Comment("Comment 1", "user1");
        LocalDateTime time1 = comment1.getCreatedAt();
        
        // Simulate a small delay
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Comment comment2 = new Comment("Comment 2", "user2");
        LocalDateTime time2 = comment2.getCreatedAt();
        
        // Second comment should have later timestamp
        assertTrue(time2.isAfter(time1) || time2.isEqual(time1));
    }
    
    @DisplayName("Should handle null content safely")
    @Test
    void testNullContent() {
        comment.setContent(null);
        
        assertNull(comment.getContent());
    }
    
    @DisplayName("Should handle null author safely")
    @Test
    void testNullAuthor() {
        comment.setCreatedBy(null);
        
        assertNull(comment.getCreatedBy());
    }
}

