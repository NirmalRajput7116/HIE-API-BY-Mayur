package com.cellbeans.hspa.actionmodule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionModuleMstRepository extends JpaRepository<ActionModuleMst, Long> {

    Page<ActionModuleMst> findAllByIsActiveTrueAndIsDeletedFalseOrderByAmName(Pageable page);

    Page<ActionModuleMst> findAllByAmNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByAmName(String actionName, Pageable page);

    List<ActionModuleMst> findByAmNameContainsAndIsActiveTrueAndIsDeletedFalse(String actionName);

    @Query(value = "SELECT count(mc.am_name) FROM action_module_mst mc WHERE mc.am_name=:am_name and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByAmName(@Param("am_name") String am_name);

}
