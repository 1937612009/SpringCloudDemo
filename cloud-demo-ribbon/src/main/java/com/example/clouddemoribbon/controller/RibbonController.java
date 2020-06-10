package com.example.clouddemoribbon.controller;

import com.example.entity.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("findAll")
    public JsonResult findAll(){
        JsonResult object = restTemplate.getForObject("http://USER-SERVER/user/findAll", JsonResult.class);
        System.out.println(object);

        return object;
    }


}
