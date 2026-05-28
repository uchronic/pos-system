package com.pos.controller;

import com.pos.common.Result;
import com.pos.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "仪表盘")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Operation(summary = "仪表盘统计数据")
    @GetMapping
    public Result<Map<String, Object>> stats() {
        Map<String, Object> data = new HashMap<>();
        data.put("productCount", productRepository.countActive());
        data.put("memberCount", memberRepository.countActive());
        data.put("orderCount", orderRepository.countPaid());
        data.put("storeCount", storeRepository.count());

        BigDecimal todayAmount = orderRepository.sumAmountByDate(
                LocalDate.now().atStartOfDay(),
                LocalDateTime.now());
        data.put("todaySales", todayAmount);

        BigDecimal totalAmount = orderRepository.sumTotalAmount();
        data.put("totalSales", totalAmount);

        return Result.ok(data);
    }
}
