package com.study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
