package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.exception.CompanyNotFoundException;
import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CompanyStatus;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.repository.CompanyRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CompanyServiceImplTest {

  @Mock private CompanyRepository companyRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private CompanyServiceImpl companyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createCompany_UserExists_CompanyCreated() {
    // Arrange
    String username = "test@example.com";
    User user = new User();
    user.setEmail(username);
    Company company = new Company();
    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
    when(companyRepository.save(any(Company.class))).thenReturn(company);

    // Act
    Company createdCompany = companyService.createCompany(company, username);

    // Assert
    assertNotNull(createdCompany);
    verify(userRepository, times(1)).findByEmail(username);
    verify(companyRepository, times(1)).save(company);
    assertEquals(CompanyStatus.ACTIVE, createdCompany.getCompanyStatus());
    assertEquals(createdCompany.getUser().getUserRole(), UserRole.COMPANY);
  }

  @Test
  void userHasCompany_UserHasCompany_ReturnsTrue() {
    // Arrange
    Long userId = 1L;
    when(companyRepository.findByUserId(userId)).thenReturn(Optional.of(new Company()));

    // Act
    Boolean hasCompany = companyService.userHasCompany(userId);

    // Assert
    assertTrue(hasCompany);
    verify(companyRepository, times(1)).findByUserId(userId);
  }

  @Test
  void findByUserEmail_UserExists_CompanyReturned() {
    // Arrange
    String email = "user@example.com";
    User user = new User();
    user.setId(1L);
    Company company = new Company();
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(companyRepository.findByUserId(user.getId())).thenReturn(Optional.of(company));

    // Act
    Company foundCompany =
        companyService
            .findByUserEmail(email)
            .orElseThrow(() -> new CompanyNotFoundException("Company not found"));

    // Assert
    assertNotNull(foundCompany);
    verify(userRepository, times(1)).findByEmail(email);
    verify(companyRepository, times(1)).findByUserId(user.getId());
  }

  @Test
  void updateCompany_UserExists_CompanyUpdated() {
    // Arrange
    String email = "update@example.com";
    User user = new User();
    Company company = new Company();
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(companyRepository.save(company)).thenReturn(company);

    // Act
    Company updatedCompany = companyService.updateCompany(company, email);

    // Assert
    assertNotNull(updatedCompany);
    verify(userRepository, times(1)).findByEmail(email);
    verify(companyRepository, times(1)).save(company);
  }
}
