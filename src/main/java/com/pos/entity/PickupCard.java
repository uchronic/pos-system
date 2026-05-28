package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pickup_card")
@Data
public class PickupCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String cardNo;

    @Column(length = 20)
    private String password;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @Column(precision = 10, scale = 2)
    private BigDecimal initialAmount;

    private Long productId;

    @Column(length = 100)
    private String productName;

    private Integer quantity = 1;

    private Long memberId;

    private Integer status = 0; // 0-未使用 1-已使用 2-已过期

    private LocalDateTime expireTime;

    private LocalDateTime usedTime;

    private LocalDateTime createdAt = LocalDateTime.now();
}
