package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.model.enums.ProductStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private ProductCategory productCategory;
  @ManyToOne private Company company;
  private Long stockCount;
  private Double price;
  private String name;
  private String description;
  private String imageUrl;
  private ProductStatus productStatus;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;

  @Transient
  private Boolean inCart;
}
