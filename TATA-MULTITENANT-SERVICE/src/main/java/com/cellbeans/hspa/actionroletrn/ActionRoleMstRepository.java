package com.cellbeans.hspa.actionroletrn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRoleMstRepository extends JpaRepository<ActionRoleMst, Long> {

    Page<ActionRoleMst> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);
    // Page<ActionRoleMst> findAllByArActionSubModuleMstAsmActionModuleIdAmNameContainsAndIsActiveTrueAndIsDeletedFalseOrArActionSubModuleMstAsmNameContainsAndIsActiveTrueAndIsDeletedFalseOrArRoleIdRoleNameContainsAndIsActiveTrueAndIsDeletedFalse(String amName, String asmName, String roleName, Pageable page);

    Page<ActionRoleMst> findAllByArRoleIdRoleIdAndIsActiveTrueAndIsDeletedFalse(String amName, String asmName, String roleName, Pageable page);

    List<ActionRoleMst> findAllByArRoleIdRoleId(long roleId);

    List<ActionRoleMst> findAllByArRoleIdRoleIdAndArActionSubModuleMstAsmIdAndIsActiveTrueAndIsDeletedFalse(long roleId, long asmId);

}
