package com.fula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zl
 * @description springboot 启动类
 * @date 2020/8/28 23:27  
 */
@Configuration
@SpringBootApplication
@EnableScheduling
public class FULAApplication {

    public static void main(String[] args) {
        SpringApplication.run(FULAApplication.class, args);
    }
}