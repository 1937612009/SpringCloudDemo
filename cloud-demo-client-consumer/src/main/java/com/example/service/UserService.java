package com.example.service;

import com.example.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "consumer-server",path = "/consumer")
@Component
public interface UserService {

    @RequestMapping("getUser")
    User getUser();

}
