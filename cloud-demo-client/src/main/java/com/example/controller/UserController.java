package com.example.controller;

import com.example.entity.JsonResult;
import com.example.entity.PageBean;
import com.example.entity.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户管理相关接口")
@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;


    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public User getUser() {
        return new User();
    }

    @ApiOperation("登录")
    @PostMapping(value = "login")
    public JsonResult login(@RequestBody @Valid User user) {
        return new JsonResult(200, "登陆成功", userService.login(user));
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "addUser")
    public JsonResult addUser(@RequestBody @Valid User user) {
        return new JsonResult(200, userService.addUser(user));
    }

    @ApiOperation("更新用户")
    @PostMapping(value = "updateUser")
    public JsonResult updateUser(@RequestBody @Valid User user) {
        return new JsonResult(200, userService.updateUser(user));
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "delUser")
    public JsonResult delUser(@RequestBody User user) {
        return new JsonResult(200, userService.delUser(user));
    }

    @ApiOperation("获取所有用户")
    @GetMapping(value = "findAll")
    public JsonResult findAll() {
        return new JsonResult(200, "获取成功", userService.findAll());
    }

    @ApiOperation("获取用户详细信息")
    @GetMapping(value = "findUser")
    public JsonResult findUser(@RequestBody User user) {
        return new JsonResult(200, "获取成功", userService.findUser(user));
    }

    @ApiOperation("分页查询用户")
    @PostMapping(value = "findLimitUser")
    public JsonResult findLimitUser(@RequestBody PageBean page) {
        return new JsonResult(200, "获取成功", userService.findLimitUser(page));
    }

    @ApiOperation("端口")
    @PostMapping(value = "index")
    public JsonResult index() {
        return new JsonResult(200, "获取成功", this.port);
    }


}
