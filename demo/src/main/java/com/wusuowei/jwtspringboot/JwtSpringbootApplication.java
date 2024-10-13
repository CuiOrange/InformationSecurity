package com.wusuowei.jwtspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wusuowei.jwtspringboot.mapper")
@SpringBootApplication
public class JwtSpringbootApplication {
//    private static final Logger logger = LoggerFactory.getLogger(JwtSpringbootApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(JwtSpringbootApplication.class, args);
    }

}
