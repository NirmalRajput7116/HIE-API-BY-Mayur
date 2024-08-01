package com.cellbeans.hspa.actionsubmodule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionSubModuleMstRepository extends JpaRepository<ActionSubModuleMst, Long> {

    Page<ActionSubModuleMst> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<ActionSubModuleMst> findAllByAsmNameContainsAndIsActiveTrueAndIsDeletedFalseOrAsmActionModuleIdAmNameContainsAndIsActiveTrueAndIsDeletedFalse(String asmName, String amName, Pageable page);

    List<ActionSubModuleMst> findByAsmNameContainsAndIsActiveTrueAndIsDeletedFalse(String actionName);

    List<ActionSubModuleMst> findAllByAsmActionModuleIdAmIdAndIsActiveTrueAndIsDeletedFalse(long amId);

    ActionSubModuleMst findAllByAsmIdAndIsActiveTrueAndIsDeletedFalse(long asmId);

    @Query(value = "SELECT count(mc.asm_name) FROM action_sub_module_mst mc WHERE mc.asm_name=:am_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByAmName(@Param("am_name") String am_name);

}
