package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.model.enums.UserStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;
  private String mobile;
  private String email;
  private String gender;
  private String password;
  private String dateOfBirth;
  private UserStatus userStatus;
  private UserRole userRole;

  private String profileImageUrl;

  private LocalDateTime registerDate;
  private LocalDateTime updateDate;
}
