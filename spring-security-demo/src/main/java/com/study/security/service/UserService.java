package com.study.security.service;

import com.study.security.dao.UserDao;
import com.study.security.entity.SysRole;
import com.study.security.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userDao.findByUsername(username).orElseThrow(() -> new RuntimeException("没有此用户"));
        Set<SysRole> roleSet = user.getRoleSet();
        List<SimpleGrantedAuthority> authorities = roleSet.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
        User result = new User(user.getUsername(), user.getPassword(),
                user.getStatus() == 1, true, true, true, authorities);
        return result;
    }
}
