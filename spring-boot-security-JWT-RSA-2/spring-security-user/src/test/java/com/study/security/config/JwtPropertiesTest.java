package com.study.security.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 11:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtPropertiesTest {

    @Autowired
    JwtProperties jwtProperties;

    @Test
    public void test() {
        System.out.println(jwtProperties);
    }

}