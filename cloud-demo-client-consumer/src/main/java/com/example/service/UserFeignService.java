package com.example.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "consumer-server",path = "/consumer")
@Component
public interface UserFeignService {

    @RequestMapping(value = "getUser")
    User getUser();

    @RequestMapping(value = "login")
    String login(@RequestBody User user);

    @RequestMapping(value = "addUser")
    public String addUser(@RequestBody @Valid User user);

    @RequestMapping(value = "updateUser")
    public String updateUser(@RequestBody @Valid User user);

    @RequestMapping(value = "delUser")
    public String delUser(@RequestBody User user);

    @RequestMapping(value = "findAll")
    public List<User> findAll();

    @RequestMapping(value = "findUser")
    public User findUser(@RequestBody User user);




}
