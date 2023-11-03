package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
/**
 * Function：add user information
 * Author：Peilongyu Wang
 * Date：2023/9/27 22:37
 */
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    /**
     * Function：update user information
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * Function：delete single user information
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    /**
     * Function：delete multiple user information
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * Function：search all user information
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList = userService.list(new QueryWrapper<User>().orderByDesc("id"));
        return Result.success(userList);
    }

    /**
     * Function：search user information by id
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }


    /**
     * Function：Multi-condition fuzzy search for user information
     * pageNum: current page number
     * pageSize: results per page
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestParam String username, @RequestParam String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(username), "username", username);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        Page<User> page = userService.page(new Page<>(pageNum,pageSize), queryWrapper);
        return Result.success(page);
    }


}