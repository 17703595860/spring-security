package com.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.config.JwtProperties;
import com.study.security.dao.UserDao;
import com.study.security.entity.SysUser;
import com.study.security.entity.UserInfo;
import com.study.security.utils.CookieUtils;
import com.study.security.utils.JwtUtils;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    @Setter
    private JwtProperties jwtProperties;
    @Setter
    private UserDao userDao;

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtVerifyFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
            if (StringUtils.isBlank(token)){
                chain.doFilter(request, response);
                throw new AuthenticationServiceException("token解析失败，登录失败");
            }else {
                UserInfo userInfo = null;
                userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
                if (userInfo == null) {
                    throw new AuthenticationServiceException("token解析失败，登录失败");
                }
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            errorResponseJson(response, e);
        }
    }

    private void errorResponseJson(HttpServletResponse response, Exception e) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "401");
        map.put("msg", StringUtils.isBlank(e.getMessage()) ? "验证失败" : e.getMessage());
        response.setContentType("application/json;charset=utf-8");
        new ObjectMapper().writeValue(response.getWriter(), map);
    }
}
