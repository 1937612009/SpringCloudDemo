package com.example.clouddemoeurekaservertwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudDemoEurekaServerTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudDemoEurekaServerTwoApplication.class, args);
    }

}
