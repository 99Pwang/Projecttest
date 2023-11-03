package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Function：Provide interface to return data
 * Author：Peilongyu Wang
 * Date：2023/9/25 20:31
 */
@RestController

public class WebController {
    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping ("/")
    public Result hello(){
        return Result.success("success");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("Username or password must not be empty");
        }
        user=userService.login(user);
        return Result.success(user);

}
}