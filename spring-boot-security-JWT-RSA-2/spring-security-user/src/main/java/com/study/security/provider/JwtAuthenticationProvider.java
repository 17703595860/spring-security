package com.study.security.provider;

import com.study.security.authentication.ApiAuthenticationToken;
import com.study.security.authentication.JwtAuthenticationToken;
import com.study.security.config.JwtProperties;
import com.study.security.dao.UserDao;
import com.study.security.entity.PayLoad;
import com.study.security.entity.SysUser;
import com.study.security.utils.JwtUtils;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/11 15:33
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Setter
    private UserDao userDao;
    @Setter
    private JwtProperties jwtProperties;

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 认证逻辑，如果token中不存放有效信息，而是存放到缓存redis中，那么就可以先访问缓存，在访问数据库
        String token = ((JwtAuthenticationToken) authentication).getToken();
        PayLoad payLoad = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        SysUser user = payLoad.getData();
        // 对比数据
        SysUser daoUser = userDao.findByUsername(user.getUsername()).orElseThrow(() -> new AuthenticationServiceException("认证失败"));
        return ApiAuthenticationToken.builder().token(token).user(daoUser).authorities(daoUser.getAuthorities()).build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
