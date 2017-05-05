package com.whale.demo.springboot.service;

import com.whale.demo.springboot.model.User;

/**
 * Created by benjaminchung on 17/5/6.
 */
public interface UserService {
    User findUserByName(String name);
}
