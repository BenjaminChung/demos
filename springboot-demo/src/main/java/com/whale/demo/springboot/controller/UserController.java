package com.whale.demo.springboot.controller;

import com.whale.demo.springboot.model.User;
import com.whale.demo.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by benjaminchung on 17/5/6.
 */
@RestController
@RequestMapping({"/home"})
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user")
    @ResponseBody
    public String user() {
        User user = userService.findUserByName("benjamin");
        return user.getName() + "-----" + user.getAge();
    }
}
