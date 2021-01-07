package com.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 11:51
 */
@SpringBootApplication
@EnableJpaRepositories
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
