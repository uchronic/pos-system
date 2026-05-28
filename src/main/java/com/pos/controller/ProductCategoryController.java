package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.ProductCategory;
import com.pos.repository.ProductCategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "商品分类")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryRepository categoryRepository;

    @Operation(summary = "分类列表")
    @GetMapping
    public Result<List<ProductCategory>> list() {
        return Result.ok(categoryRepository.findByStatusOrderBySortOrder(1));
    }

    @Operation(summary = "新增分类")
    @PostMapping
    public Result<ProductCategory> create(@RequestBody ProductCategory category) {
        return Result.ok(categoryRepository.save(category));
    }

    @Operation(summary = "更新分类")
    @PutMapping("/{id}")
    public Result<ProductCategory> update(@PathVariable Long id, @RequestBody ProductCategory category) {
        category.setId(id);
        return Result.ok(categoryRepository.save(category));
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return Result.ok();
    }
}
