package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.enums.CartStatus;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
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
}
