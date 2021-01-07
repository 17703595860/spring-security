package com.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/5 11:43
 */
public class JwtAuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> result = new HashMap<String, Object>() {{
            put("code", "401");
            put("msg", "认证异常");
        }};
        objectMapper.writeValue(response.getWriter(), result);
    }
}
