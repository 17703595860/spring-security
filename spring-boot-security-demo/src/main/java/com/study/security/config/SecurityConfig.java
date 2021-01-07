package com.study.security.config;

import com.study.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 访问数据库
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    // 认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 认证
        http.authorizeRequests()
                .antMatchers("/login.jsp", "failer.jsp", "css/**", "img/**", "/plugins/**").permitAll()
                // 可以使用hasAnyRole()或者hasAnyAuthority()，这儿使用什么，上面认证就得使用什么
                .antMatchers("/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                // 登录设置
                .formLogin()
                .loginProcessingUrl("/login")       // 登录请求的url
                .loginPage("/login.jsp")            // 自定义的登录页面
                .successForwardUrl("/index.jsp")    // 登录成功后转发的路径
                .failureForwardUrl("/failer.jsp")   // 登录失败后跳转的页面
                .permitAll()
                .and()
                // 退出登录配置
                .logout()
                .logoutUrl("/logout")           // 退出登录的请求地址
                .logoutSuccessUrl("/login.jsp") // 退出成功的url
                .invalidateHttpSession(true)    // 清除session
                .permitAll()
                .and()
                // 关闭csrf
                .csrf().disable();
    }

    /**
     * 密码加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCryptPasswordEncoder加密
        return new BCryptPasswordEncoder();
        // 使用md5加密（不推荐）
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return Objects.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()), encodedPassword);
//            }
//        };
    }
}
