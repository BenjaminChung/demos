package com.whale.demo.springboot.service.impl;

import com.whale.demo.springboot.mapper.UserMapper;
import com.whale.demo.springboot.model.User;
import com.whale.demo.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by benjaminchung on 17/5/6.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }
}
