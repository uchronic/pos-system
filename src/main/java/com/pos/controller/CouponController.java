package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.Coupon;
import com.pos.repository.CouponRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "营销管理-优惠券")
@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponRepository couponRepository;

    @Operation(summary = "优惠券列表")
    @GetMapping
    public Result<List<Coupon>> list() {
        return Result.ok(couponRepository.findAll());
    }

    @Operation(summary = "新增优惠券")
    @PostMapping
    public Result<Coupon> create(@RequestBody Coupon coupon) {
        return Result.ok(couponRepository.save(coupon));
    }

    @Operation(summary = "更新优惠券")
    @PutMapping("/{id}")
    public Result<Coupon> update(@PathVariable Long id, @RequestBody Coupon coupon) {
        coupon.setId(id);
        return Result.ok(couponRepository.save(coupon));
    }

    @Operation(summary = "删除优惠券")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        couponRepository.deleteById(id);
        return Result.ok();
    }
}
