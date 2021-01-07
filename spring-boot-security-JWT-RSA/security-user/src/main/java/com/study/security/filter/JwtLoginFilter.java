package com.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.config.JwtProperties;
import com.study.security.entity.SysPermission;
import com.study.security.entity.SysRole;
import com.study.security.entity.SysUser;
import com.study.security.entity.UserInfo;
import com.study.security.utils.CookieUtils;
import com.study.security.utils.JwtUtils;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/30 11:15
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Setter
    private AuthenticationManager authenticationManager;
    @Setter
    private JwtProperties jwtProperties;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String method = request.getMethod();
        // 请求方式必须是post
        if (!StringUtils.equalsIgnoreCase(method, "post")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        SysUser user = new ObjectMapper().readValue(request.getInputStream(), SysUser.class);


        String username = user.getUsername();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = user.getPassword();
        password = (password != null) ? password : "";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        SysUser user = (SysUser) authResult.getPrincipal();
        Set<SysPermission> authories = user.getRoleSet().stream().map(SysRole::getPermissionSet).flatMap(Collection::stream).collect(Collectors.toSet());
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), authories);
        try {
            String token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire()*60, "UTF-8");
            response.setContentType("application/json;charset=utf-8");
            HashMap<String, Object> result = new HashMap<String, Object>() {{
                put("code", "200");
                put("msg", token);
            }};
            new ObjectMapper().writeValue(response.getWriter(), result);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Token生成失败");
        }
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> result = new HashMap<String, Object>() {{
            put("code", "401");
            put("msg", failed.getMessage());
        }};
        new ObjectMapper().writeValue(response.getWriter(), result);
    }

}
