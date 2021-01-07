package com.study.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author HLH
 * @email 17703595860@163.com
 * @date : Created in  2020/12/31 10:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayLoad implements Serializable {

    private static final long serialVersionUID = -9018394576021806992L;

    private SysUser data;
    private Integer expire;

}
