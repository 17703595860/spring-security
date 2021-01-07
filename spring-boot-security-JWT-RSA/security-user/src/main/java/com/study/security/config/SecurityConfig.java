package com.study.security.config;

import com.study.security.dao.UserDao;
import com.study.security.filter.JwtLoginFilter;
import com.study.security.filter.JwtVerifyFilter;
import com.study.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtProperties jwtProperties;

    private JwtLoginFilter getJwtLoginFilter() throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setJwtProperties(jwtProperties);
        jwtLoginFilter.setAuthenticationManager(this.authenticationManager());
        return jwtLoginFilter;
    }

    private JwtVerifyFilter getJwtVerifyFilter() throws Exception {
        JwtVerifyFilter jwtVerifyFilter = new JwtVerifyFilter(this.authenticationManager());
        jwtVerifyFilter.setJwtProperties(jwtProperties);
        jwtVerifyFilter.setUserDao(userDao);
        return jwtVerifyFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getJwtLoginFilter())
                .addFilter(getJwtVerifyFilter())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
