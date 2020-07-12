package com.example.ossystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class OssystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssystemApplication.class, args);
    }

}
