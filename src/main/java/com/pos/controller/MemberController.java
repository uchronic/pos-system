package com.pos.controller;

import com.pos.common.PageResult;
import com.pos.common.Result;
import com.pos.entity.Member;
import com.pos.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "会员管理")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @Operation(summary = "会员列表")
    @GetMapping
    public Result<PageResult<Member>> list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Member> result = name.isEmpty()
                ? memberRepository.findAll(pageRequest)
                : memberRepository.findByNameContaining(name, pageRequest);
        return Result.ok(PageResult.of(result));
    }

    @Operation(summary = "会员详情")
    @GetMapping("/{id}")
    public Result<Member> detail(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(Result::ok)
                .orElse(Result.error("会员不存在"));
    }

    @Operation(summary = "新增会员")
    @PostMapping
    public Result<Member> create(@RequestBody Member member) {
        return Result.ok(memberRepository.save(member));
    }

    @Operation(summary = "更新会员")
    @PutMapping("/{id}")
    public Result<Member> update(@PathVariable Long id, @RequestBody Member member) {
        member.setId(id);
        return Result.ok(memberRepository.save(member));
    }

    @Operation(summary = "删除会员")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        memberRepository.deleteById(id);
        return Result.ok();
    }
}
