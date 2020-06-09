package com.example.service;

import com.example.entity.JsonResult;
import com.example.entity.PageBean;
import com.example.entity.User;
import com.example.service.impl.FeignError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@FeignClient(name = "user-server",path = "/user",fallback = FeignError.class)
public interface UserFeignService {

    @RequestMapping(value = "getUser")
    User getUser();

    @PostMapping(value = "login")
    public JsonResult login(@RequestBody @Valid User user);

    @PostMapping(value = "addUser")
    public JsonResult addUser(@RequestBody @Valid User user);

    @PostMapping(value = "updateUser")
    public JsonResult updateUser(@RequestBody @Valid User user);

    @PostMapping(value = "delUser")
    public JsonResult delUser(@RequestBody User user);

    @GetMapping(value = "findAll")
    public JsonResult findAll();

    @GetMapping(value = "findUser")
    public JsonResult findUser(@RequestBody User user);

    @PostMapping(value = "findLimitUser")
    public JsonResult findLimitUser(@RequestBody PageBean page);

    @PostMapping(value = "index")
    public JsonResult index();




}
