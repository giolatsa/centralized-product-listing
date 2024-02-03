package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    private Long id;
    private String name;
    private String email;
    @ManyToOne
    private User user;
    @ManyToOne
    private CompanyCategory companyCategory;
    private CompanyStatus companyStatus;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

}
