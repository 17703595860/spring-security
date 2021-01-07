package com.study.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
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
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String username;
    private String password;
    private Integer status;

    @ManyToMany(targetEntity = SysRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Builder.Default
    private Set<SysRole> roleSet = new HashSet<>();

}
