package com.pos.controller;

import com.pos.common.PageResult;
import com.pos.common.Result;
import com.pos.entity.Order;
import com.pos.entity.OrderItem;
import com.pos.repository.OrderItemRepository;
import com.pos.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Operation(summary = "订单列表")
    @GetMapping
    public Result<PageResult<Order>> list(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Order> result;
        if (status != null) {
            result = orderRepository.findByStatus(status, pageRequest);
        } else if (memberId != null) {
            result = orderRepository.findByMemberId(memberId, pageRequest);
        } else {
            result = orderRepository.findAll(pageRequest);
        }
        return Result.ok(PageResult.of(result));
    }

    @Operation(summary = "订单详情")
    @GetMapping("/{id}")
    public Result<Order> detail(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(Result::ok)
                .orElse(Result.error("订单不存在"));
    }

    @Operation(summary = "订单商品明细")
    @GetMapping("/{id}/items")
    public Result<List<OrderItem>> items(@PathVariable Long id) {
        return Result.ok(orderItemRepository.findByOrderId(id));
    }

    @Operation(summary = "创建订单")
    @PostMapping
    public Result<Order> create(@RequestBody Order order) {
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setCreatedAt(LocalDateTime.now());
        return Result.ok(orderRepository.save(order));
    }

    @Operation(summary = "订单支付")
    @PutMapping("/{id}/pay")
    public Result<Order> pay(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(1);
            order.setPayTime(LocalDateTime.now());
            return Result.ok(orderRepository.save(order));
        }).orElse(Result.error("订单不存在"));
    }

    @Operation(summary = "取消订单")
    @PutMapping("/{id}/cancel")
    public Result<Order> cancel(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(4);
            return Result.ok(orderRepository.save(order));
        }).orElse(Result.error("订单不存在"));
    }
}
