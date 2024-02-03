package com.epam.centralized.product.listing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    private Long id;
    private String categoryName;
    private LocalDateTime createDate;
    private LocalDateTime UpdateDate;

}
