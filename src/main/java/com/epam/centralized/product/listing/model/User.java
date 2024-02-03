package com.epam.centralized.product.listing.model;

import com.epam.centralized.product.listing.enums.UserRole;
import com.epam.centralized.product.listing.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserStatus userStatus;
    private UserRole userRole;
    private LocalDateTime registerDate;
    private LocalDateTime updateDate;

}
