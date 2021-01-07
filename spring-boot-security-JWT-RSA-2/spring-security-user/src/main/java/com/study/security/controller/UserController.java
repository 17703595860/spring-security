package com.study.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 11:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String findAll() {
        return "Success";
    }

}
