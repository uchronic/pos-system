package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String orderNo;

    private Long memberId;

    @Column(length = 20)
    private String memberName;

    private Long storeId;

    private Long cashierId;

    @Column(length = 30)
    private String cashierName;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal payAmount;

    @Column(length = 20)
    private String payType; // WECHAT/ALIPAY/CASH/CARD

    private Integer status = 0; // 0-待支付 1-已支付 2-已完成 3-已退款 4-已取消

    @Column(length = 200)
    private String remark;

    private Integer itemCount = 0;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime payTime;

    private LocalDateTime updatedAt = LocalDateTime.now();
}
