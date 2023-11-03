package com.example.springboot.service;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
/**
 * Function：
 * Author：Peilongyu Wang
 * Date：2023/9/27 22:34
 */
public class UserService extends ServiceImpl<UserMapper,User> {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean save(User entity){
        if(StrUtil.isBlank(entity.getName())){
            entity.setName(entity.getUsername());
        }
        if(StrUtil.isBlank(entity.getPassword())){
            entity.setPassword("123456");
        }
        return super.save(entity);
    }

    public User selectByUsername(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        //Query the database for user information based on the username
        return getOne(queryWrapper);
    }

    //Verify if the user account is valid
    public User login(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser == null){
            //Throw a custom exception
            throw new ServiceException("Invalid account or password");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("Invalid account or password");
        }
        //create token
        String token = TokenUtils.createToken(dbUser.getId().toString(),dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }
}