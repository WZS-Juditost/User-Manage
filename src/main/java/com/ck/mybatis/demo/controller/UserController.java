package com.ck.mybatis.demo.controller;


import com.ck.mybatis.demo.common.Result;
import com.ck.mybatis.demo.entity.User;
import com.ck.mybatis.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        try {
            return Result.ok(userService.login(user.getUserName(), user.getPassword()));
        } catch (RuntimeException e) {
            return Result.fail("登陆失败: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<Boolean> create(@RequestBody User user) {
        try {
            return Result.ok(userService.insert(user) != 0, "注册成功");
        } catch (RuntimeException e) {
            return Result.fail("注册失败: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable("id") Long id) {
        return Result.ok(userService.deleteById(id) > 0);
    }
}
