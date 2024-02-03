package com.epam.centralized.product.listing.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Entity
@Table(name = "order")
public class Order {

@Id
    private Long id;
@ManyToOne
    private User user;
    private Long productCount;
    @OneToOne
    private Cart cart;



}
