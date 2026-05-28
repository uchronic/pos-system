package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.Employee;
import com.pos.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "员工管理")
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Operation(summary = "员工列表")
    @GetMapping
    public Result<List<Employee>> list() {
        return Result.ok(employeeRepository.findAll());
    }

    @Operation(summary = "员工详情")
    @GetMapping("/{id}")
    public Result<Employee> detail(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(Result::ok)
                .orElse(Result.error("员工不存在"));
    }

    @Operation(summary = "新增员工")
    @PostMapping
    public Result<Employee> create(@RequestBody Employee employee) {
        return Result.ok(employeeRepository.save(employee));
    }

    @Operation(summary = "更新员工")
    @PutMapping("/{id}")
    public Result<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setId(id);
        return Result.ok(employeeRepository.save(employee));
    }

    @Operation(summary = "删除员工")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return Result.ok();
    }
}
