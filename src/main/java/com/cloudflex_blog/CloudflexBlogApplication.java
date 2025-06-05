package com.cloudflex_blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.cloudflex_blog.modules")
@SpringBootApplication
public class CloudflexBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudflexBlogApplication.class, args);
    }

}
