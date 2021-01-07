package com.study.security.dao;

import com.study.security.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface RoleDao extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {

    Set<SysRole> findByIdIn(List<Long> ids);

    Optional<SysRole> findByRoleName(String roleName);

}
