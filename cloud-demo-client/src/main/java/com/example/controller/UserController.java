package com.example.controller;

import com.example.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "getUser")
    public User getUser(){
        return new User();
    }
}
