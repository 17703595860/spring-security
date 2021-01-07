package com.study.security.controller;

import com.study.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(userDao.findAll());
    }

}
