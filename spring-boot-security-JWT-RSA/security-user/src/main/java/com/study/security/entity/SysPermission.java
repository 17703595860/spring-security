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

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@ToString(exclude = "roleSet")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sys_permission")
@JsonIgnoreProperties({"roleSet"})
public class SysPermission implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String permissionName;
    private String permissionDesc;
    private String parentId;

    @ManyToMany(targetEntity = SysRole.class, mappedBy = "permissionSet", fetch = FetchType.EAGER)
    @Builder.Default
    private Set<SysRole> roleSet = new HashSet<>();

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.permissionName;
    }
}
