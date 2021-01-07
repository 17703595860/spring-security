package com.study.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -6913541008753630719L;

    private Long id;
    private String username;
    private Set<SysPermission> authorities;

}
