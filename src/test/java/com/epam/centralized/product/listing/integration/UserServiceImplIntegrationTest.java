package com.epam.centralized.product.listing.integration;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.model.enums.UserStatus;
import com.epam.centralized.product.listing.service.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceImplIntegrationTest {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  @Autowired private UserService userService;
  private User testUser;

  @BeforeEach
  void setUp() {
    // Create and save a test user
    testUser = new User();
    testUser.setEmail("test@example.com");
    testUser.setPassword(bCryptPasswordEncoder.encode("1"));
    testUser.setUserRole(UserRole.CUSTOMER);
    testUser.setUserStatus(UserStatus.ACTIVE);
    testUser.setRegisterDate(LocalDateTime.now());
    userService.createUser(testUser);
  }

  @Test
  void findByEmail_UserExists_ReturnsUser() {
    User foundUser = userService.findByEmail(testUser.getEmail());
    assertNotNull(foundUser);
    assertEquals(testUser.getEmail(), foundUser.getEmail());
  }

  @Test
  void updateUserById_UpdatesUser_Successfully() {
    testUser.setEmail("updated@example.com");
    User updatedUser = userService.updateUserById(testUser, testUser.getId());
    assertNotNull(updatedUser);
    assertEquals("updated@example.com", updatedUser.getEmail());
  }

  @Test
  void changeUserPassword_ChangesPassword_Successfully() {
    userService.changeUserPassword(testUser, "newPassword");
    User updatedUser = userService.findByEmail(testUser.getEmail());
    assertTrue(bCryptPasswordEncoder.matches("newPassword", updatedUser.getPassword()));
  }

  @Test
  void createUser_CreatesUser_Successfully() {
    User newUser = new User();
    newUser.setEmail("newuser@example.com");
    newUser.setPassword("newPassword");
    userService.createUser(newUser);

    User foundUser = userService.findByEmail("newuser@example.com");
    assertNotNull(foundUser);
    assertTrue(bCryptPasswordEncoder.matches("newPassword", foundUser.getPassword()));
    assertEquals(UserRole.CUSTOMER, foundUser.getUserRole());
    assertEquals(UserStatus.ACTIVE, foundUser.getUserStatus());
  }
}
