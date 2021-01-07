package com.study.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString(exclude = "roleSet")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties({"roleSet"})
// 直接继承UserDetails
public class SysUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer status;

    @ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<SysRole> roleSet = new HashSet<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleSet;
    }

    // 是否启动
    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }

    // 账户失效
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 密码失效
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户已被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

}
