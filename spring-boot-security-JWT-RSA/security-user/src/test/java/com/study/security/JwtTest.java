package com.study.security;

import com.study.security.config.JwtProperties;
import com.study.security.entity.UserInfo;
import com.study.security.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@Rollback(false)
public class JwtTest {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() throws Exception {
        /*UserInfo userInfo = new UserInfo(1L, "aa", null);
        System.out.println(jwtProperties);
        String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        System.out.println(token);*/
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJ1c2VyIjoie1wiaWRcIjoxLFwidXNlcm5hbWVcIjpcImFkbWluXCIsXCJhdXRob3JpdGllc1wiOlt7XCJpZFwiOjEsXCJwZXJtaXNzaW9uTmFtZVwiOlwiYWRtaW5cIixcInBlcm1pc3Npb25EZXNjXCI6XCIvYWRtaW5cIixcInBhcmVudElkXCI6XCIwXCJ9LHtcImlkXCI6MixcInBlcm1pc3Npb25OYW1lXCI6XCJ1c2VyXCIsXCJwZXJtaXNzaW9uRGVzY1wiOlwiL3VzZXJcIixcInBhcmVudElkXCI6XCIxXCJ9XX0iLCJleHAiOjE2MDkzMTM4NTJ9.FbRwRIQkqxUCix9H1_w5l6UuPIAr_6Dw2YGRpUs4wm1pTQqHBLgk0Oc6wK6_1cRc6xQtPE7cmPCE4Bn6oiFcmcMdGGXnWu7OFVHppcKMQ7nGwuQ5oDOhWoSX3M2aeDB6qvgF8BoJj3EOfqhoX-580sZ3DR6vKsT6ZP267a_vxANRLkKDn7BncXr5fSIFE8jyuy9PxScCxHMwPk-nIgNAhbGzU1uXDPDdk-c1fWo8NStYqoiZf8ECgvpePR1bQmHQuSvQax2EQ9qqpSCn2mThU8dSB2RPZmc1-m6DDbGF1QwVvloZUwT2ABOOCXxb-XOrBF10EXwjQXAJy5n1PaKvtA";
        UserInfo userInfo1 = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        System.out.println(userInfo1);
    }

    @Test
    public void test1() throws Exception {
        System.out.println(passwordEncoder.encode("1234"));
    }


}