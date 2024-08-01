package com.cellbeans.hspa.actionbutton;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionButtonMstRepository extends JpaRepository<ActionButtonMst, Long> {

    Page<ActionButtonMst> findAllByIsActiveTrueAndIsDeletedFalseOrderByAbName(Pageable page);

    Page<ActionButtonMst> findAllByAbNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByAbName(String abName, Pageable page);

    List<ActionButtonMst> findAllByAbIdAndIsActiveTrueAndIsDeletedFalse(long abId);

    @Query(value = "SELECT count(mc.ab_name) FROM action_button_mst mc WHERE mc.ab_name=:buttonName and mc.is_deleted=false", nativeQuery = true)
    int findByAllOrderByButtonName(@Param("buttonName") String buttonName);

}
