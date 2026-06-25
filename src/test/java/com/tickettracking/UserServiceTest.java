package com.tickettracking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserService.
 * Tests default users, roles, current user handling, and user management.
 */
@DisplayName("UserService Tests")
class UserServiceTest {
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
    }
    
    @DisplayName("Should initialize with default users")
    @Test
    void testDefaultUsersInitialization() {
        List<User> users = userService.getAllUsers();
        
        assertNotNull(users, "Users list should not be null");
        assertEquals(4, users.size(), "Should have 4 default users");
    }
    
    @DisplayName("Should have admin user as first default user")
    @Test
    void testAdminUserExists() {
        List<User> users = userService.getAllUsers();
        User adminUser = users.get(0);
        
        assertEquals("admin", adminUser.getUsername());
        assertEquals("admin", adminUser.getPassword());
        assertEquals(User.Role.ADMIN, adminUser.getRole());
    }
    
    @DisplayName("Should have support staff users")
    @Test
    void testSupportStaffUsersExist() {
        List<User> users = userService.getAllUsers();
        List<User> supportStaff = users.stream()
            .filter(u -> u.getRole() == User.Role.SUPPORT_STAFF)
            .toList();
        
        assertEquals(2, supportStaff.size(), "Should have 2 support staff users");
        assertTrue(supportStaff.stream().anyMatch(u -> u.getUsername().equals("support1")));
        assertTrue(supportStaff.stream().anyMatch(u -> u.getUsername().equals("support2")));
    }
    
    @DisplayName("Should have customer user")
    @Test
    void testCustomerUserExists() {
        List<User> users = userService.getAllUsers();
        User customerUser = users.stream()
            .filter(u -> u.getRole() == User.Role.CUSTOMER)
            .findFirst()
            .orElse(null);
        
        assertNotNull(customerUser);
        assertEquals("customer1", customerUser.getUsername());
        assertEquals(User.Role.CUSTOMER, customerUser.getRole());
    }
    
    @DisplayName("Should set current user to support1 by default")
    @Test
    void testCurrentUserDefaultValue() {
        User currentUser = userService.getCurrentUser();
        
        assertNotNull(currentUser);
        assertEquals("support1", currentUser.getUsername());
        assertEquals(User.Role.SUPPORT_STAFF, currentUser.getRole());
    }
    
    @DisplayName("Should set current user")
    @Test
    void testSetCurrentUser() {
        User admin = userService.findUserByUsername("admin");
        
        userService.setCurrentUser(admin);
        
        assertEquals(admin, userService.getCurrentUser());
        assertEquals("admin", userService.getCurrentUser().getUsername());
    }
    
    @DisplayName("Should find user by username")
    @Test
    void testFindUserByUsername() {
        User foundUser = userService.findUserByUsername("support1");
        
        assertNotNull(foundUser, "Should find user support1");
        assertEquals("support1", foundUser.getUsername());
        assertEquals(User.Role.SUPPORT_STAFF, foundUser.getRole());
    }
    
    @DisplayName("Should return null for non-existent username")
    @Test
    void testFindNonExistentUser() {
        User foundUser = userService.findUserByUsername("nonexistent");
        
        assertNull(foundUser, "Should return null for non-existent user");
    }
    
    @DisplayName("Should get all usernames")
    @Test
    void testGetUsernames() {
        List<String> usernames = userService.getUsernames();
        
        assertEquals(4, usernames.size());
        assertTrue(usernames.contains("admin"));
        assertTrue(usernames.contains("support1"));
        assertTrue(usernames.contains("support2"));
        assertTrue(usernames.contains("customer1"));
    }
    
    @DisplayName("Should return copy of users list")
    @Test
    void testGetAllUsersReturnsCopy() {
        List<User> users1 = userService.getAllUsers();
        List<User> users2 = userService.getAllUsers();
        
        assertNotSame(users1, users2, "Should return different list instances");
        assertEquals(users1.size(), users2.size(), "Lists should have same content");
    }
    
    @DisplayName("Should not modify internal list when modifying returned list")
    @Test
    void testGetAllUsersImmutability() {
        List<User> users = userService.getAllUsers();
        int originalSize = users.size();
        
        // Try to modify returned list
        users.clear();
        
        // Original should be unchanged
        List<User> usersAfter = userService.getAllUsers();
        assertEquals(originalSize, usersAfter.size(), 
            "Internal list should not be modified");
    }
    
    @DisplayName("Should change current user multiple times")
    @Test
    void testMultipleCurrentUserChanges() {
        User admin = userService.findUserByUsername("admin");
        User customer = userService.findUserByUsername("customer1");
        
        assertEquals("support1", userService.getCurrentUser().getUsername());
        
        userService.setCurrentUser(admin);
        assertEquals("admin", userService.getCurrentUser().getUsername());
        
        userService.setCurrentUser(customer);
        assertEquals("customer1", userService.getCurrentUser().getUsername());
    }
    
    @DisplayName("Should support all user roles")
    @Test
    void testAllUserRoles() {
        User.Role[] roles = User.Role.values();
        
        assertEquals(3, roles.length, "Should have 3 role types");
        assertTrue(roleExists(roles, User.Role.ADMIN));
        assertTrue(roleExists(roles, User.Role.SUPPORT_STAFF));
        assertTrue(roleExists(roles, User.Role.CUSTOMER));
    }
    
    @DisplayName("Should maintain user credentials")
    @Test
    void testUserCredentialsPreserved() {
        User admin = userService.findUserByUsername("admin");
        
        assertEquals("admin", admin.getUsername());
        assertEquals("admin", admin.getPassword());
    }
    
    private boolean roleExists(User.Role[] roles, User.Role targetRole) {
        for (User.Role role : roles) {
            if (role == targetRole) return true;
        }
        return false;
    }
}

