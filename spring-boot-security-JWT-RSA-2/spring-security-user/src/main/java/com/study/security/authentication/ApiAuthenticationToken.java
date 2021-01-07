package com.study.security.authentication;

import com.study.security.entity.SysUser;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2021/1/5 16:53
 */
@Getter
@Setter
@Builder
public class ApiAuthenticationToken implements Authentication {

    private static final long serialVersionUID = 2099911009172429863L;

    private SysUser user;

    private String token;

    private Collection<GrantedAuthority> authorities;

    private Object details;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (!isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token unauthenticated");
        }
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
