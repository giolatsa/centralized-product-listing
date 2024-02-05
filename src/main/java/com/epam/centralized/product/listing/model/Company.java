package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.enums.CompanyStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  @ManyToOne private User user;
  @ManyToOne private CompanyCategory companyCategory;
  private CompanyStatus companyStatus;
  private LocalDateTime registerDate;
  private LocalDateTime updateDate;
}
