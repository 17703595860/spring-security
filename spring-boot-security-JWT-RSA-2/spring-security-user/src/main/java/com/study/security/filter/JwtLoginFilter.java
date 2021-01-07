package com.study.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.security.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 12:29
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Setter
    private ObjectMapper objectMapper;
    @Setter
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        SysUser user = null;
        try {
            user = objectMapper.readValue(request.getInputStream(), SysUser.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException("参数错误，登录失败");
        }

        String username = user.getUsername();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = user.getPassword();
        password = (password != null) ? password : "";

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }

}
