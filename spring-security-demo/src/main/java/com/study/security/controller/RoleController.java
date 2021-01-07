package com.study.security.controller;

import com.study.security.dao.PermissionDao;
import com.study.security.dao.RoleDao;
import com.study.security.entity.SysPermission;
import com.study.security.entity.SysRole;
import com.study.security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @GetMapping("/findAll")
    public String findAll(Model model, Integer page, Integer size){
        if (page == null || page < 0) page = 0;
        if (size == null || size < 0) size = 5;
        Page<SysRole> rolePage = roleDao.findAll(PageRequest.of(page, size));
        model.addAttribute("list", rolePage);
        return "role-list";
    }

    @PostMapping("/save")
    public String save(SysRole role){
        Optional<SysRole> roleOptional = roleDao.findByRoleName(role.getRoleName());
        if (roleOptional.isPresent()){
            throw new RuntimeException("角色已经存在");
        }
        roleDao.save(role);
        return "redirect:findAll";
    }

    @GetMapping("/toAddPermissionPage")
    public String toAddRolePage(Model model, Long id, boolean success){
        List<SysPermission> allPermission = permissionDao.findAll();
        SysRole role = roleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("不存在"));
        List<Long> myPermission = role.getPermissionSet().stream().map(SysPermission::getId).collect(Collectors.toList());
        model.addAttribute("rid", id);
        model.addAttribute("allPermission", allPermission);
        model.addAttribute("myPermission", myPermission);
        if (success){
            model.addAttribute("success", "修改成功");
        }
        return "role-permission-add";
    }

    @PostMapping("/addPermissionToRole")
    public String addRoleToUser(Long[] ids, Long roleId){
        Set<SysPermission> permissionList = permissionDao.findByIdIn(Arrays.asList(ids));
        SysRole role = roleDao.findById(roleId)
                .orElseThrow(() -> new RuntimeException("不存在"));
        role.getPermissionSet().addAll(permissionList);
        roleDao.save(role);
        return "redirect:toAddPermissionPage?success="+true+"&id="+roleId;
    }
    
}
