package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private CustomUserDetailsService userDetailsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUserByUsername_UserExists_ReturnsUserDetails() {
    // Arrange
    String username = "test@example.com";
    User user = new User();
    user.setEmail(username);
    user.setPassword("password");
    user.setUserRole(UserRole.CUSTOMER);
    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

    // Act
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Assert
    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
    assertEquals("password", userDetails.getPassword());
    assertTrue(
        userDetails.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER")));
    verify(userRepository, times(1)).findByEmail(username);
  }

  @Test
  void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
    // Arrange
    String username = "nonexistent@example.com";
    when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

    // Assert
    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          // Act
          userDetailsService.loadUserByUsername(username);
        });
    verify(userRepository, times(1)).findByEmail(username);
  }
}
