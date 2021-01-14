package com.study.security.Authorization;

import lombok.Setter;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/14 16:20
 */
public class ApiMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public ApiMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Setter
    private ApiPermissionEvaluator apiPermissionEvaluator;

    // 扩展，有某个权限
    public boolean hasPermission(String permission) {
        return apiPermissionEvaluator.hasAnyPermission(this.getAuthentication(), permission);
    }
    // 扩展，有权限列表中的任意一个
    public boolean hasAnyPermission(String ... permission) {
        return apiPermissionEvaluator.hasAnyPermission(this.getAuthentication(), permission);
    }
    // 扩展，同时有权限列表中的所有权限
    public boolean hasAndPermission(String ... permission) {
        return apiPermissionEvaluator.hasAndPermission(this.getAuthentication(), permission);
    }

    /**
     * 复制与 MethodSecurityExpressionRoot 类，主要是为了扩展方法
     */
    private Object filterObject;

    private Object returnObject;

    private Object target;

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return this.target;
    }
}
