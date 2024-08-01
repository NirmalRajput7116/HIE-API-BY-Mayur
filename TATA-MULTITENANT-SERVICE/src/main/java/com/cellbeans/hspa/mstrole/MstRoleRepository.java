package com.cellbeans.hspa.mstrole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MstRoleRepository extends JpaRepository<MstRole, Long> {

    Page<MstRole> findByRoleNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByRoleName(String name, Pageable page);

    List<MstRole> findByRoleNameContainsAndIsActiveTrueAndIsDeletedFalse(String roleName);

    Page<MstRole> findAllByIsActiveTrueAndIsDeletedFalseOrderByRoleName(Pageable page);

    List<MstRole> findByRoleNameContains(String key);

    @Query(value = "SELECT count(mc.role_name) FROM mst_role mc WHERE mc.role_name=:roleName and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByRoleName(@Param("roleName") String roleName);

}
            
