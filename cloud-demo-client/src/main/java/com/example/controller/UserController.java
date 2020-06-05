package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "getUser")
    public User getUser(){
        return new User();
    }

    @RequestMapping(value = "login")
    public String login(@RequestBody @Valid User user){
        return userService.login(user);
    }
    @RequestMapping(value = "addUser")
    public String addUser(@RequestBody @Valid User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "updateUser")
    public String updateUser(@RequestBody @Valid User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "delUser")
    public String delUser(@RequestBody User user){
        return userService.delUser(user);
    }

    @RequestMapping(value = "findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "findUser")
    public User findUser(@RequestBody User user){
        return userService.findUser(user);
    }



}
