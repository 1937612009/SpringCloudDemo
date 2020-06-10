package com.example.service.impl;

import com.example.entity.JsonResult;
import com.example.entity.PageBean;
import com.example.entity.User;
import com.example.service.UserFeignService;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class FeignError implements UserFeignService {
    @Override
    public User getUser() {
        return null;
    }

    @Override
    public JsonResult login(@Valid User user) {
        return null;
    }

    @Override
    public JsonResult addUser(@Valid User user) {
        return null;
    }

    @Override
    public JsonResult updateUser(@Valid User user) {
        return null;
    }

    @Override
    public JsonResult delUser(User user) {
        return null;
    }

    @Override
    public JsonResult findAll() {
        return null;
    }

    @Override
    public JsonResult findUser(User user) {
        return null;
    }

    @Override
    public JsonResult findLimitUser(PageBean page) {
        return null;
    }

    @Override
    public JsonResult index() {
        return new JsonResult(500,"服务器优化中。。。");
    }
}
