package com.study.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.dao.UserDao;
import com.study.security.filter.JwtLoginFilter;
import com.study.security.filter.JwtVerifyFilter;
import com.study.security.handler.*;
import com.study.security.provider.ApiAuthenticationProvider;
import com.study.security.provider.JwtAuthenticationProvider;
import com.study.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 13:29
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    public JwtLoginFilter getJwtLoginFilter() throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setObjectMapper(objectMapper);
        // 指定登录地址和方式，注入成功和失败的处理类
        jwtLoginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        jwtLoginFilter.setAuthenticationSuccessHandler(getLoginSuccessHandler());
        jwtLoginFilter.setAuthenticationFailureHandler(getLoginFailureHandler());

        AuthenticationManager authenticationManager = this.authenticationManager();
        jwtLoginFilter.setAuthenticationManager(authenticationManager);
        return jwtLoginFilter;
    }

    public JwtVerifyFilter getJwtVerifyFilter() throws Exception {
        JwtVerifyFilter jwtVerifyFilter = new JwtVerifyFilter();
        jwtVerifyFilter.setJwtProperties(jwtProperties);
        jwtVerifyFilter.setObjectMapper(objectMapper);
        jwtVerifyFilter.setUserDao(userDao);
        jwtVerifyFilter.setAuthenticationManager(this.authenticationManager());
        return jwtVerifyFilter;
    }

    public LoginSuccessHandler getLoginSuccessHandler() {
        LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler();
        loginSuccessHandler.setJwtProperties(jwtProperties);
        loginSuccessHandler.setObjectMapper(objectMapper);
        return loginSuccessHandler;
    }

    public LoginFailureHandler getLoginFailureHandler() {
        LoginFailureHandler loginFailureHandler = new LoginFailureHandler();
        loginFailureHandler.setObjectMapper(objectMapper);
        return loginFailureHandler;
    }

    public JwtLogoutHandler getJwtLogoutHandler() {
        JwtLogoutHandler jwtLogoutHandler = new JwtLogoutHandler();
        jwtLogoutHandler.setJwtProperties(jwtProperties);
        jwtLogoutHandler.setObjectMapper(objectMapper);
        return jwtLogoutHandler;
    }

    public JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler() {
        JwtAuthenticationExceptionHandler jwtAuthenticationExceptionHandler = new JwtAuthenticationExceptionHandler();
        jwtAuthenticationExceptionHandler.setObjectMapper(objectMapper);
        return jwtAuthenticationExceptionHandler;
    }

    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        JwtAccessDeniedHandler jwtAccessDeniedHandler = new JwtAccessDeniedHandler();
        jwtAccessDeniedHandler.setObjectMapper(objectMapper);
        return jwtAccessDeniedHandler;
    }

    public ApiAuthenticationProvider apiAuthenticationProvider() {
        ApiAuthenticationProvider apiAuthenticationProvider = new ApiAuthenticationProvider();
        apiAuthenticationProvider.setUserDetailsService(userService);
        apiAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return apiAuthenticationProvider;
    }

    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();
        jwtAuthenticationProvider.setJwtProperties(jwtProperties);
        jwtAuthenticationProvider.setUserDao(userDao);
        return jwtAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(apiAuthenticationProvider());
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtLogoutHandler jwtLogoutHandler = getJwtLogoutHandler();
        http.csrf().disable()
                .rememberMe().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // 退出登录设置
                .logout().logoutUrl("/logout").addLogoutHandler(jwtLogoutHandler).logoutSuccessHandler(jwtLogoutHandler).permitAll()
                .and()
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationExceptionHandler())
                .accessDeniedHandler(jwtAccessDeniedHandler())
                .and()
                .addFilterBefore(getJwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getJwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
