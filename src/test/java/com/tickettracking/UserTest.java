package com.tickettracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for User.
 * Tests user creation, role assignment, and field management.
 */
@DisplayName("User Tests")
class UserTest {
    
    private User user;
    
    @BeforeEach
    void setUp() {
        user = new User();
    }
    
    @DisplayName("Should create user with default constructor")
    @Test
    void testDefaultConstructor() {
        User newUser = new User();
        
        assertNull(newUser.getId());
        assertNull(newUser.getUsername());
        assertNull(newUser.getPassword());
        assertNull(newUser.getRole());
    }
    
    @DisplayName("Should create user with parameters")
    @Test
    void testParameterizedConstructor() {
        User newUser = new User("testuser", "password123", User.Role.ADMIN);
        
        assertEquals("testuser", newUser.getUsername());
        assertEquals("password123", newUser.getPassword());
        assertEquals(User.Role.ADMIN, newUser.getRole());
    }
    
    @DisplayName("Should set and get ID")
    @Test
    void testSetGetId() {
        user.setId("user123");
        
        assertEquals("user123", user.getId());
    }
    
    @DisplayName("Should set and get username")
    @Test
    void testSetGetUsername() {
        user.setUsername("john_doe");
        
        assertEquals("john_doe", user.getUsername());
    }
    
    @DisplayName("Should set and get password")
    @Test
    void testSetGetPassword() {
        user.setPassword("securePassword123");
        
        assertEquals("securePassword123", user.getPassword());
    }
    
    @DisplayName("Should set and get role")
    @Test
    void testSetGetRole() {
        user.setRole(User.Role.SUPPORT_STAFF);
        
        assertEquals(User.Role.SUPPORT_STAFF, user.getRole());
    }
    
    @DisplayName("Should support all role types")
    @Test
    void testAllRoles() {
        user.setRole(User.Role.ADMIN);
        assertEquals(User.Role.ADMIN, user.getRole());
        
        user.setRole(User.Role.SUPPORT_STAFF);
        assertEquals(User.Role.SUPPORT_STAFF, user.getRole());
        
        user.setRole(User.Role.CUSTOMER);
        assertEquals(User.Role.CUSTOMER, user.getRole());
    }
    
    @DisplayName("Should have correct toString representation")
    @Test
    void testToString() {
        user.setUsername("testuser");
        
        assertEquals("testuser", user.toString());
    }
    
    @DisplayName("Should handle null username in toString")
    @Test
    void testToStringWithNullUsername() {
        user.setUsername(null);
        
        assertNull(user.toString());
    }
    
    @DisplayName("Should set all fields independently")
    @Test
    void testIndependentFieldSetting() {
        user.setId("1");
        user.setUsername("user1");
        user.setPassword("pass1");
        user.setRole(User.Role.CUSTOMER);
        
        assertEquals("1", user.getId());
        assertEquals("user1", user.getUsername());
        assertEquals("pass1", user.getPassword());
        assertEquals(User.Role.CUSTOMER, user.getRole());
        
        // Change one field
        user.setPassword("pass2");
        
        // Others should remain unchanged
        assertEquals("1", user.getId());
        assertEquals("user1", user.getUsername());
        assertEquals("pass2", user.getPassword());
        assertEquals(User.Role.CUSTOMER, user.getRole());
    }
    
    @DisplayName("Should handle special characters in username")
    @Test
    void testSpecialCharactersInUsername() {
        user.setUsername("user@example.com");
        
        assertEquals("user@example.com", user.getUsername());
    }
    
    @DisplayName("Should handle empty string username")
    @Test
    void testEmptyStringUsername() {
        user.setUsername("");
        
        assertEquals("", user.getUsername());
    }
}


