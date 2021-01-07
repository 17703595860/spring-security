package com.study.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping("/product")
//@RolesAllowed({"ROLE_ADMIN"})
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
//@Secured({"ROLE_ADMIN"})
public class ProductController {
    @RequestMapping("/findAll")
    public String findAll(){
        return "product-list";
    }
}
