package com.epam.centralized.product.listing.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private User user;
  private Long productCount;
  @OneToOne private Cart cart;
}
