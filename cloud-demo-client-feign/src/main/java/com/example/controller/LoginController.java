package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.PageBean;
import com.example.entity.User;
import com.example.service.UserFeignService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private UserFeignService userService;


    @RequestMapping(value = "getConsumer")
    public User getConsumer() {
        return userService.getUser();
    }



    @RequestMapping(value = "toLogin")
    public JsonResult login(@RequestBody User user){
       return userService.login(user);
    }

    @RequestMapping(value = "toAddUser")
    public JsonResult addUser(@RequestBody @Valid User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "toUpdateUser")
    public JsonResult updateUser(@RequestBody @Valid User user){
        return userService.updateUser(user);
    }

    @RequestMapping(value = "toDelUser")
    public JsonResult delUser(@RequestBody User user){
        return userService.delUser(user);
    }

    @RequestMapping(value = "toFindAll")
    public JsonResult findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "toFindUser")
    public JsonResult findUser(@RequestBody User user){
        return userService.findUser(user);
    }

    @ApiOperation("分页查询用户")
    @PostMapping(value = "toFindLimitUser")
    public JsonResult findLimitUser(@RequestBody PageBean page) {
        return userService.findLimitUser(page);
    }

    @ApiOperation("端口")
    @PostMapping(value = "toIndex")
    public JsonResult index() {
        return userService.index();
    }


}
