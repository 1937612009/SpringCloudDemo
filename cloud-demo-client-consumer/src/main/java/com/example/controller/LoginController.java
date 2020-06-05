package com.example.controller;

import com.example.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserFeignService userService;


    @RequestMapping(value = "getConsumer")
    public User getConsumer() {
        return userService.getUser();
    }



    @RequestMapping(value = "toLogin")
    public String login(@RequestBody User user){
       return userService.login(user);
    }

    @RequestMapping(value = "toAddUser")
    public String addUser(@RequestBody @Valid User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "toUpdateUser")
    public String updateUser(@RequestBody @Valid User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "toDelUser")
    public String delUser(@RequestBody User user){
        return userService.delUser(user);
    }

    @RequestMapping(value = "toFindAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "toFindUser")
    public User findUser(@RequestBody User user){
        return userService.findUser(user);
    }


}
