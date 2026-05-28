package com.pos.repository;

import com.pos.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderNo(String orderNo);
    Page<Order> findByStatus(Integer status, Pageable pageable);
    Page<Order> findByMemberId(Long memberId, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 1")
    long countPaid();

    @Query("SELECT COALESCE(SUM(o.payAmount),0) FROM Order o WHERE o.status IN (1,2) AND o.createdAt BETWEEN :start AND :end")
    BigDecimal sumAmountByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(o.payAmount),0) FROM Order o WHERE o.status IN (1,2)")
    BigDecimal sumTotalAmount();
}
