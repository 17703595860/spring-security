package com.study.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 12:55
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Setter
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        response.setStatus(401);
//        response.setContentType("application/json;charset=utf-8");
//        HashMap<String, Object> result = new HashMap<String, Object>() {{
//            put("code", "401");
//            put("msg", exception.getMessage() == null ? "登录失败" : exception.getMessage());
//        }};
//        objectMapper.writeValue(response.getWriter(), result);
        throw exception;
    }

}
