package com.pos.controller;

import com.pos.common.PageResult;
import com.pos.common.Result;
import com.pos.entity.Supplier;
import com.pos.repository.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "供应商管理")
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierRepository supplierRepository;

    @Operation(summary = "供应商列表")
    @GetMapping
    public Result<PageResult<Supplier>> list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Supplier> result = name.isEmpty()
                ? supplierRepository.findAll(pageRequest)
                : supplierRepository.findByNameContaining(name, pageRequest);
        return Result.ok(PageResult.of(result));
    }

    @Operation(summary = "新增供应商")
    @PostMapping
    public Result<Supplier> create(@RequestBody Supplier supplier) {
        return Result.ok(supplierRepository.save(supplier));
    }

    @Operation(summary = "更新供应商")
    @PutMapping("/{id}")
    public Result<Supplier> update(@PathVariable Long id, @RequestBody Supplier supplier) {
        supplier.setId(id);
        return Result.ok(supplierRepository.save(supplier));
    }

    @Operation(summary = "删除供应商")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return Result.ok();
    }
}
