package com.study.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */

/**
 *  boolean prePostEnabled() default false;
 *      Spring注解支持  PreAuthorize("hasAnyRole('ROLE_ADMIN'), 'ROLE_PRODUCT'")
 *  boolean securedEnabled() default false;
 *      Security注解支持    Secured({"ROLE_ADMIN", "ROLE_PRODUCT"})
 *  boolean jsr250Enabed() default false;
 *      Jsr250注解支持  RolesAllowed({"ROLE_ADMIN", "ROLE_PRODUCT"})
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
