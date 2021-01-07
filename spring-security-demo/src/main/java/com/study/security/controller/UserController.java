package com.study.security.controller;

import com.study.security.dao.RoleDao;
import com.study.security.dao.UserDao;
import com.study.security.entity.SysRole;
import com.study.security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @GetMapping("/findAll")
    public String findAll(Model model, Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size < 0) size = 5;
        Page<SysUser> userPage = userDao.findAll(PageRequest.of(page, size));
        model.addAttribute("list", userPage);
        return "user-list";
    }

    @PostMapping("/save")
    public String save(SysUser user) {
        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("用户已经存在");
        }
        userDao.save(user);
        return "redirect:findAll";
    }

    @GetMapping("/toAddRolePage")
    public String toAddRolePage(Model model, Long id, boolean success) {
        List<SysRole> allRoles = roleDao.findAll();
        SysUser user = userDao.findById(id)
                .orElseThrow(() -> new RuntimeException("不存在"));
        List<Long> myRoles = user.getRoleSet().stream().map(SysRole::getId).collect(Collectors.toList());
        model.addAttribute("uid", id);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("myRoles", myRoles);
        if (success) {
            model.addAttribute("success", "修改成功");
        }
        return "user-role-add";
    }

    @PostMapping("/addRoleToUser")
    public String addRoleToUser(Long[] ids, Long userId) {
        Set<SysRole> roleSet = roleDao.findByIdIn(Arrays.asList(ids));
        SysUser user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("不存在"));
        user.getRoleSet().addAll(roleSet);
        userDao.save(user);
        return "redirect:toAddRolePage?success=" + true + "&id=" + userId;
    }




}
