package com.pos.controller;

import com.pos.common.Result;
import com.pos.entity.Employee;
import com.pos.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "登录认证")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeRepository employeeRepository;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        Employee emp = employeeRepository.findByUsername(username);
        if (emp == null || !emp.getPassword().equals(password)) {
            return Result.error(401, "用户名或密码错误");
        }
        if (emp.getStatus() != 1) {
            return Result.error(403, "账号已禁用");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("token", "demo-token-" + emp.getId());
        data.put("userId", emp.getId());
        data.put("username", emp.getUsername());
        data.put("name", emp.getName());
        data.put("role", emp.getRole());
        data.put("storeId", emp.getStoreId());
        return Result.ok(data);
    }
}
