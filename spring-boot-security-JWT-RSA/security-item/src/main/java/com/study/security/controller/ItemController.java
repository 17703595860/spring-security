package com.study.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/30 11:11
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @GetMapping
    @PreAuthorize("hasAuthority('item')")
    public String findAll() {
        return "Success";
    }

}
