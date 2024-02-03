package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.enums.ProductStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private Long id;
    @ManyToOne
    private ProductCategory productCategory;
    @ManyToOne
    private Company company;
    private Long stockCount;
    private Long price;
    private String name;
    private ProductStatus productStatus;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
