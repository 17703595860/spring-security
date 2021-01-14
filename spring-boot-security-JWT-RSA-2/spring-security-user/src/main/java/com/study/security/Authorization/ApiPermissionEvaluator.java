package com.study.security.Authorization;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/14 14:55
 */
public class ApiPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    /**
     * 扩展的表达式，接收permission数组，只要有其中一个权限，即可放行
     */
    public boolean hasAnyPermission(Authentication authentication, String... permissionArray) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> permissionList = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        for (String permission: permissionArray) {
            if (!permissionList.contains(permission)){
                return false;
            }
        }
        return true;
    }

    /**
     * 扩展的表达式，接收permission数组，必须同时拥有所有权限，才放行
     */
    public boolean hasAndPermission(Authentication authentication, String[] permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> permissionList = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return permissionList.containsAll(Arrays.asList(permission));
    }
}
