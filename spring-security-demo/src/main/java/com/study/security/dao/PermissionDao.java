package com.study.security.dao;

import com.study.security.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface PermissionDao extends JpaRepository<SysPermission, Long>, JpaSpecificationExecutor<SysPermission> {

    Optional<SysPermission> findByPermissionName(String permissionName);

    Set<SysPermission> findByIdIn(List<Long> idList);

}
