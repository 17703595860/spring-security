package com.study.security.controller;

import com.study.security.dao.PermissionDao;
import com.study.security.entity.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionDao permissionDao;

    @GetMapping("/findAll")
    public String findAll(Model model, Integer page, Integer size){
        if (page == null || page < 0) page = 0;
        if (size == null || size < 0) size = 5;
        Page<SysPermission> permissionPage = permissionDao.findAll(PageRequest.of(page, size));
        model.addAttribute("list", permissionPage);
        return "permission-list";
    }

    @PostMapping("/save")
    public String save(SysPermission permission){
        Optional<SysPermission> entityOptional = permissionDao.findByPermissionName(permission.getPermissionName());
        if (entityOptional.isPresent()){
            throw new RuntimeException("权限已经存在");
        }
        permissionDao.save(permission);
        return "redirect:findAll";
    }
    
}
