package com.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.config.JwtProperties;
import com.study.security.entity.UserInfo;
import com.study.security.utils.CookieUtils;
import com.study.security.utils.JwtUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/30 11:15
 */
@Slf4j
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    @Setter
    private JwtProperties jwtProperties;

    
    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtVerifyFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName(), "UTF-8");
            if (StringUtils.isBlank(token)) {
                throw new AuthorizationServiceException("token为空");
            }
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authResult);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("token解析失败", e);
            response.setContentType("application/json;charset=utf-8");
            HashMap<String, Object> result = new HashMap<String, Object>() {{
                put("code", "401");
                put("msg", e.getMessage());
            }};
            new ObjectMapper().writeValue(response.getWriter(), result);
        }
    }
}
