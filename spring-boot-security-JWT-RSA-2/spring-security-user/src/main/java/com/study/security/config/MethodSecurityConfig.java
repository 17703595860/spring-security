package com.study.security.config;

import com.study.security.Authorization.ApiMethodSecurityExpressionHandler;
import com.study.security.Authorization.ApiPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/14 15:59
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * 在构造方法之后，直接注入
     */
    @PostConstruct
    public void postConstruct(){
        ApiMethodSecurityExpressionHandler apiMethodSecurityExpressionHandler = new ApiMethodSecurityExpressionHandler();
        apiMethodSecurityExpressionHandler.setPermissionEvaluator(new ApiPermissionEvaluator());
        this.setMethodSecurityExpressionHandler(Collections.singletonList(apiMethodSecurityExpressionHandler));
    }

    /**
     * 重写createExpressionHandler
     */
//    @Override
//    protected MethodSecurityExpressionHandler createExpressionHandler() {
//        ApiMethodSecurityExpressionHandler apiMethodSecurityExpressionHandler = new ApiMethodSecurityExpressionHandler();
//        apiMethodSecurityExpressionHandler.setPermissionEvaluator(new ApiPermissionEvaluator());
//        return apiMethodSecurityExpressionHandler;
//    }

}
