package com.study.security.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
public class UserServiceTest {

    @Test
    public void loadUserByUsername() {
//        String s = DigestUtils.md5DigestAsHex("123".getBytes());
//        System.out.println(s);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
        boolean matches = bCryptPasswordEncoder.matches("123", "$2a$10$QAj1rYEa.K1WkOpfuDFZUuO2Fyux2t2QPUsB4xScGPunSl0vg8bQi");
        System.out.println(matches);
    }
}