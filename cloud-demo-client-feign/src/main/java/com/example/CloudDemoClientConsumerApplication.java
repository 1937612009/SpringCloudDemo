package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan(value = "com.example.*")
public class CloudDemoClientConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDemoClientConsumerApplication.class, args);
    }

}
