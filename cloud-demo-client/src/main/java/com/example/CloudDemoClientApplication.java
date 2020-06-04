package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient  //将服务注册到注册中心上
@EnableFeignClients  //开启Fegin客户端
@ComponentScan(value = "com.example.*")
public class CloudDemoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDemoClientApplication.class, args);
    }

}
