package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String type; // FULL_REDUCE(满减) / DISCOUNT(折扣) / FIXED(固定金额)

    @Column(precision = 10, scale = 2)
    private BigDecimal minAmount = BigDecimal.ZERO; // 最低消费

    @Column(precision = 10, scale = 2)
    private BigDecimal value; // 优惠值

    private Integer totalCount = 0;

    private Integer usedCount = 0;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status = 1; // 1-启用 0-禁用

    private LocalDateTime createdAt = LocalDateTime.now();
}
