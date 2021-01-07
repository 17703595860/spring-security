package com.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@SpringBootApplication
@EnableJpaRepositories
public class JwtSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class);
    }
}
