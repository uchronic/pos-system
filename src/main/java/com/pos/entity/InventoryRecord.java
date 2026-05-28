package com.pos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_record")
@Data
public class InventoryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Column(length = 100)
    private String productName;

    @Column(length = 20)
    private String type; // IN(入库) / OUT(出库) / ADJUST(盘点调整)

    private Integer quantity;

    @Column(length = 200)
    private String reason;

    private Long operatorId;

    @Column(length = 30)
    private String operatorName;

    private LocalDateTime createdAt = LocalDateTime.now();
}
