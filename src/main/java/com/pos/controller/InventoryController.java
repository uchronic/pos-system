package com.pos.controller;

import com.pos.common.PageResult;
import com.pos.common.Result;
import com.pos.entity.InventoryRecord;
import com.pos.entity.Product;
import com.pos.repository.InventoryRecordRepository;
import com.pos.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "进销存管理")
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRecordRepository inventoryRecordRepository;
    private final ProductRepository productRepository;

    @Operation(summary = "库存记录列表")
    @GetMapping("/records")
    public Result<PageResult<InventoryRecord>> records(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<InventoryRecord> result;
        if (productId != null) {
            result = inventoryRecordRepository.findByProductId(productId, pageRequest);
        } else if (type != null && !type.isEmpty()) {
            result = inventoryRecordRepository.findByType(type, pageRequest);
        } else {
            result = inventoryRecordRepository.findAll(pageRequest);
        }
        return Result.ok(PageResult.of(result));
    }

    @Operation(summary = "入库")
    @PostMapping("/stock-in")
    public Result<InventoryRecord> stockIn(@RequestBody InventoryRecord record) {
        record.setType("IN");
        updateProductStock(record.getProductId(), record.getQuantity());
        return Result.ok(inventoryRecordRepository.save(record));
    }

    @Operation(summary = "出库")
    @PostMapping("/stock-out")
    public Result<InventoryRecord> stockOut(@RequestBody InventoryRecord record) {
        record.setType("OUT");
        updateProductStock(record.getProductId(), -record.getQuantity());
        return Result.ok(inventoryRecordRepository.save(record));
    }

    private void updateProductStock(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
        });
    }
}
