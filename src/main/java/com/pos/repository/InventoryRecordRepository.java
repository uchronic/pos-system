package com.pos.repository;

import com.pos.entity.InventoryRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRecordRepository extends JpaRepository<InventoryRecord, Long> {
    Page<InventoryRecord> findByProductId(Long productId, Pageable pageable);
    Page<InventoryRecord> findByType(String type, Pageable pageable);
}
