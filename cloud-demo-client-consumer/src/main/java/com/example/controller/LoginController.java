package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;


    @RequestMapping("getConsumer")
    public User getConsumer() {
        return userService.getUser();
    }

    @RequestMapping("login")
    public boolean login(String username,String password){
        User consumer = getConsumer();
        if (username.equals(consumer.getUsername()) && password.equals(consumer.getPassword())){
            return true;
        }
        return false;
    }


}
