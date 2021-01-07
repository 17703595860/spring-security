package com.study.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString(exclude = {"userSet", "permissionSet"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_role")
@JsonIgnoreProperties({"userSet", "permissionSet"})
// 直接继承GrantedAuthority
public class SysRole implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String roleDesc;

    @ManyToMany(targetEntity = SysUser.class, mappedBy = "roleSet", fetch = FetchType.EAGER)
    @Builder.Default
    private Set<SysUser> userSet = new HashSet<>();

    @ManyToMany(targetEntity = SysPermission.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<SysPermission> permissionSet = new HashSet<>();

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
