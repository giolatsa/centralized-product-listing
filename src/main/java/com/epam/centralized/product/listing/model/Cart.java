package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.model.enums.CartStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private User user;

  @ManyToMany
  @JoinTable(
      name = "cart_products",
      joinColumns = @JoinColumn(name = "cart_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;

  private CartStatus cartStatus;

  private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
