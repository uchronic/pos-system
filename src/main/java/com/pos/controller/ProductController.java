package com.pos.controller;

import com.pos.common.PageResult;
import com.pos.common.Result;
import com.pos.entity.Product;
import com.pos.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品管理")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @Operation(summary = "商品列表")
    @GetMapping
    public Result<PageResult<Product>> list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> result;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        if (categoryId != null) {
            result = productRepository.findByCategoryId(categoryId, pageRequest);
        } else if (!name.isEmpty()) {
            result = productRepository.findByNameContaining(name, pageRequest);
        } else {
            result = productRepository.findAll(pageRequest);
        }
        return Result.ok(PageResult.of(result));
    }

    @Operation(summary = "商品详情")
    @GetMapping("/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(Result::ok)
                .orElse(Result.error("商品不存在"));
    }

    @Operation(summary = "新增商品")
    @PostMapping
    public Result<Product> create(@RequestBody Product product) {
        return Result.ok(productRepository.save(product));
    }

    @Operation(summary = "更新商品")
    @PutMapping("/{id}")
    public Result<Product> update(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return Result.ok(productRepository.save(product));
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return Result.ok();
    }
}
