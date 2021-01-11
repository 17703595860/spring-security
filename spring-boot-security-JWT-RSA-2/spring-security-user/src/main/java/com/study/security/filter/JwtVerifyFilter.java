package com.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.authentication.ApiAuthenticationToken;
import com.study.security.config.JwtProperties;
import com.study.security.dao.UserDao;
import com.study.security.entity.PayLoad;
import com.study.security.entity.SysPermission;
import com.study.security.entity.SysRole;
import com.study.security.entity.SysUser;
import com.study.security.utils.CookieUtils;
import com.study.security.utils.JwtUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 12:58
 */
public class JwtVerifyFilter extends OncePerRequestFilter {

    @Setter
    private JwtProperties jwtProperties;
    @Setter
    private ObjectMapper objectMapper;
    @Setter
    private UserDao userDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName(), "UTF-8");
            PayLoad payLoad = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            SysUser user = payLoad.getData();
            user = userDao.findById(user.getId()).orElseThrow(() ->new AuthenticationServiceException("认证失败"));
            Set<SysPermission> authorities = user.getRoleSet().stream().map(SysRole::getPermissionSet).flatMap(Collection::stream).collect(Collectors.toSet());
            ApiAuthenticationToken authentication = ApiAuthenticationToken.builder().user(user).token(token).authorities(authorities).build();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException | AccessDeniedException e){
            throw e;
        } catch (Exception exception) {
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            HashMap<String, Object> result = new HashMap<String, Object>() {{
                put("code", "401");
                put("msg", exception.getMessage() == null ? "登录失败" : exception.getMessage());
            }};
            objectMapper.writeValue(response.getWriter(), result);
        }
    }

}
