package com.epam.centralized.product.listing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "company_category")
public class CompanyCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long companyId;
  private String categoryName;
  private LocalDateTime createDate;
  private LocalDateTime UpdateDate;
}
