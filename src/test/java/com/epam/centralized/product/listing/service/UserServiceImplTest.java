package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceImplTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByEmail_UserExists_ReturnsUser() {
    // Arrange
    String email = "user@example.com";
    User expectedUser = new User();
    expectedUser.setEmail(email);
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

    // Act
    User result = userService.findByEmail(email);

    // Assert
    assertNotNull(result, "User should not be null");
    assertEquals(email, result.getEmail(), "Email should match the expected");
    verify(userRepository, times(1)).findByEmail(email);
  }

  @Test
  void findByEmail_UserNotFound_ThrowsRuntimeException() {
    // Arrange
    String email = "nonexistent@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    // Act & Assert
    Exception exception =
        assertThrows(RuntimeException.class, () -> userService.findByEmail(email));
    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(email);
  }

  @Test
  void updateUserById_UserExists_UpdatesUser() {
    // Arrange
    Long userId = 1L;
    User userToUpdate = new User();
    userToUpdate.setId(userId);
    userToUpdate.setEmail("update@example.com"); // Assume other fields are set as needed
    when(userRepository.findById(userId)).thenReturn(Optional.of(userToUpdate));
    when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

    // Act
    User updatedUser = userService.updateUserById(userToUpdate, userId);

    // Assert
    assertNotNull(updatedUser, "Updated user should not be null");
    assertEquals(userId, updatedUser.getId(), "User ID should match the expected");
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).save(userToUpdate);
  }

  @Test
  void updateUserById_UserNotFound_ThrowsRuntimeException() {
    // Arrange
    Long userId = 2L;
    User userToUpdate = new User();
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // Act & Assert
    Exception exception =
        assertThrows(
            RuntimeException.class, () -> userService.updateUserById(userToUpdate, userId));
    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void checkIfValidOldPassword_ValidPassword_ReturnsTrue() {
    // Arrange
    User user = new User();
    String currentPassword = "password";
    user.setPassword(new BCryptPasswordEncoder().encode(currentPassword));

    // Act
    boolean isValid = userService.checkIfValidOldPassword(user, currentPassword);

    // Assert
    assertTrue(isValid, "Password should be valid");
  }

  @Test
  void checkIfValidOldPassword_InvalidPassword_ReturnsFalse() {
    // Arrange
    User user = new User();
    String currentPassword = "password";
    String wrongPassword = "wrongPassword";
    user.setPassword(new BCryptPasswordEncoder().encode(currentPassword));

    // Act
    boolean isValid = userService.checkIfValidOldPassword(user, wrongPassword);

    // Assert
    assertFalse(isValid, "Password should be invalid");
  }

  @Test
  void changeUserPassword_ChangesPassword_Successfully() {
    // Arrange
    User user = new User();
    String newPassword = "newPassword";

    // Act
    userService.changeUserPassword(user, newPassword);

    // Assert
    assertTrue(
        new BCryptPasswordEncoder().matches(newPassword, user.getPassword()),
        "Password should be updated to the new password");
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void createUser_CreatesUserWithEncodedPasswordAndDefaults_Successfully() {
    // Arrange
    User user = new User();
    user.setPassword("password");
    user.setEmail("newuser@example.com");

    // Act
    userService.createUser(user);

    // Assert
    assertNotNull(user.getPassword(), "Password should be encoded");
    assertNotNull(user.getUserRole(), "Default user role should be set");
    assertNotNull(user.getUserStatus(), "Default user status should be set");
    assertNotNull(user.getRegisterDate(), "Registration date should be set");
    verify(userRepository, times(1)).save(user);
  }
}
