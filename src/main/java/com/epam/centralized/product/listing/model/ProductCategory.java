package com.epam.centralized.product.listing.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String categoryName;
  private LocalDateTime createDate;

  private LocalDateTime updateDate;
}
