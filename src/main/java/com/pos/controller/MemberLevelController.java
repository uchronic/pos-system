package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.MemberLevel;
import com.pos.repository.MemberLevelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "会员等级")
@RestController
@RequestMapping("/api/member-levels")
@RequiredArgsConstructor
public class MemberLevelController {

    private final MemberLevelRepository levelRepository;

    @Operation(summary = "等级列表")
    @GetMapping
    public Result<List<MemberLevel>> list() {
        return Result.ok(levelRepository.findByStatusOrderByLevel(1));
    }

    @Operation(summary = "新增等级")
    @PostMapping
    public Result<MemberLevel> create(@RequestBody MemberLevel level) {
        return Result.ok(levelRepository.save(level));
    }

    @Operation(summary = "更新等级")
    @PutMapping("/{id}")
    public Result<MemberLevel> update(@PathVariable Long id, @RequestBody MemberLevel level) {
        level.setId(id);
        return Result.ok(levelRepository.save(level));
    }

    @Operation(summary = "删除等级")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        levelRepository.deleteById(id);
        return Result.ok();
    }
}
