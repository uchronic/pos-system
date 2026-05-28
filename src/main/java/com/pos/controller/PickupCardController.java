package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.PickupCard;
import com.pos.repository.PickupCardRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "提货卡管理")
@RestController
@RequestMapping("/api/pickup-cards")
@RequiredArgsConstructor
public class PickupCardController {

    private final PickupCardRepository pickupCardRepository;

    @Operation(summary = "提货卡列表")
    @GetMapping
    public Result<List<PickupCard>> list() {
        return Result.ok(pickupCardRepository.findAll());
    }

    @Operation(summary = "提货卡详情")
    @GetMapping("/{id}")
    public Result<PickupCard> detail(@PathVariable Long id) {
        return pickupCardRepository.findById(id)
                .map(Result::ok)
                .orElse(Result.error("提货卡不存在"));
    }

    @Operation(summary = "核销提货卡")
    @PutMapping("/{cardNo}/redeem")
    public Result<PickupCard> redeem(@PathVariable String cardNo) {
        PickupCard card = pickupCardRepository.findByCardNo(cardNo);
        if (card == null) return Result.error("提货卡不存在");
        if (card.getStatus() != 0) return Result.error("提货卡已使用或已过期");
        card.setStatus(1);
        card.setUsedTime(LocalDateTime.now());
        return Result.ok(pickupCardRepository.save(card));
    }

    @Operation(summary = "新增提货卡")
    @PostMapping
    public Result<PickupCard> create(@RequestBody PickupCard card) {
        return Result.ok(pickupCardRepository.save(card));
    }

    @Operation(summary = "删除提货卡")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        pickupCardRepository.deleteById(id);
        return Result.ok();
    }
}
