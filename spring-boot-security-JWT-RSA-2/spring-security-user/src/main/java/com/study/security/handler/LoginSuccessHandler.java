package com.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.authentication.ApiAuthenticationToken;
import com.study.security.config.JwtProperties;
import com.study.security.entity.PayLoad;
import com.study.security.entity.SysUser;
import com.study.security.utils.CookieUtils;
import com.study.security.utils.JwtUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 12:35
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Setter
    private JwtProperties jwtProperties;
    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUser user = ((ApiAuthenticationToken) authentication).getUser();
        user.setPassword(null);
        PayLoad payLoad = new PayLoad(user, jwtProperties.getExpire());
        String token = null;
        try {
            token = JwtUtils.generateToken(payLoad, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            throw new AuthenticationServiceException("token生成失败");
        }
        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire()*60, "UTF-8");

        String finalToken = token;
        HashMap<String, Object> result = new HashMap<String, Object>() {{
            put("code", "200");
            put("msg", finalToken);
        }};
        objectMapper.writeValue(response.getWriter(), result);
    }
}
