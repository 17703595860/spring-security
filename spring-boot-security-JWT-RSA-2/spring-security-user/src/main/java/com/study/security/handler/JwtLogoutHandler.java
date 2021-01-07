package com.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.config.JwtProperties;
import com.study.security.entity.SysUser;
import com.study.security.utils.CookieUtils;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/4 15:39
 */
public class JwtLogoutHandler implements LogoutHandler, LogoutSuccessHandler {

    @Setter
    private JwtProperties jwtProperties;
    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 清楚cookie
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), null, 1);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 相应信息
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> result = new HashMap<String, Object>() {{
            put("code", "200");
            put("msg", "退出成功");
        }};
        objectMapper.writeValue(response.getWriter(), result);
    }
}
