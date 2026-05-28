package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "member_level")
@Data
public class MemberLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private Integer level = 0;

    @Column(precision = 5, scale = 2)
    private BigDecimal discount = BigDecimal.ONE;

    private Integer pointsRate = 1; // 积分倍率

    @Column(precision = 10, scale = 2)
    private BigDecimal minAmount = BigDecimal.ZERO; // 升级门槛

    private Integer status = 1;
}
