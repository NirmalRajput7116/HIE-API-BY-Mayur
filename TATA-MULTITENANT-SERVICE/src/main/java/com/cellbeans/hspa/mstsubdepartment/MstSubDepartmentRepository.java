package com.cellbeans.hspa.mstsubdepartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstSubDepartmentRepository extends JpaRepository<MstSubDepartment, Long> {

    Page<MstSubDepartment> findBySdNameContainsAndIsActiveTrueAndIsDeletedFalseOrderBySdName(String name, Pageable page);

    Page<MstSubDepartment> findBySdDepartmentIdDepartmentIdAndIsActiveTrueAndIsDeletedFalseOrderBySdName(long departmentId, Pageable page);

    Page<MstSubDepartment> findAllByIsActiveTrueAndIsDeletedFalseOrderBySdName(Pageable page);

    List<MstSubDepartment> findBySdNameContains(String key);

    List<MstSubDepartment> findBySdDepartmentIdDepartmentIdEqualsAndIsActiveTrueAndIsDeletedFalse(long key);

    List<MstSubDepartment> findAllBySdDepartmentIdDepartmentIdEqualsAndSdDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderBySdName(Long sdId, Long unitId);

    MstSubDepartment findBySdNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
//    List<MstSubDepartment>  getbydepartmentlistdropdown(Long sdId,Long unitId);
}
            
