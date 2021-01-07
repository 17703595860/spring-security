package com.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/5 11:47
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(403);
        response.setContentType("application/json;charset=utf-8");
        HashMap<String, Object> result = new HashMap<String, Object>() {{
            put("code", "403");
            put("msg", "授权异常");
        }};
        objectMapper.writeValue(response.getWriter(), result);
    }
}
