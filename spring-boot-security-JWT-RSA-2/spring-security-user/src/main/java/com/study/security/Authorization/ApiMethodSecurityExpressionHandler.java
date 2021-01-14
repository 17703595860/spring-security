package com.study.security.Authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/14 16:12
 */
public class ApiMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        ApiMethodSecurityExpressionRoot root = new ApiMethodSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(getTrustResolver());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setDefaultRolePrefix(getDefaultRolePrefix());
        // 设置ApiPermissionEvaluator，扩展hasPermission和hasAnyPermission
        root.setApiPermissionEvaluator((ApiPermissionEvaluator) getPermissionEvaluator());
        return root;
    }
}
