package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.Store;
import com.pos.repository.StoreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "连锁门店管理")
@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @Operation(summary = "门店列表")
    @GetMapping
    public Result<List<Store>> list() {
        return Result.ok(storeRepository.findAll());
    }

    @Operation(summary = "新增门店")
    @PostMapping
    public Result<Store> create(@RequestBody Store store) {
        return Result.ok(storeRepository.save(store));
    }

    @Operation(summary = "更新门店")
    @PutMapping("/{id}")
    public Result<Store> update(@PathVariable Long id, @RequestBody Store store) {
        store.setId(id);
        return Result.ok(storeRepository.save(store));
    }

    @Operation(summary = "删除门店")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        storeRepository.deleteById(id);
        return Result.ok();
    }
}
