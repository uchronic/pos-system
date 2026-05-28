package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(unique = true, length = 30)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String phone;

    private Long storeId;

    @Column(length = 50)
    private String role; // ADMIN/MANAGER/CASHIER

    private Integer status = 1;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
