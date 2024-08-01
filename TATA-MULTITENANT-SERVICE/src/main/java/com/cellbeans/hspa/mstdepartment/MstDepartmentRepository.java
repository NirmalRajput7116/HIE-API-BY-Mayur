package com.cellbeans.hspa.mstdepartment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDepartmentRepository extends JpaRepository<MstDepartment, Long> {

    Page<MstDepartment> findByDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentHeadContainsAndIsActiveTrueAndIsDeletedFalseOrDepartmentUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(String name, String name1, String name2, Pageable page);

    Page<MstDepartment> findByDepartmentNameContainsAndDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalseOrDepartmentHeadContainsAndDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalseOrDepartmentUnitIdUnitNameContainsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(String name, String name1, String name2, Pageable page);

    Page<MstDepartment> findByDepartmentNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDepartment> findAllByIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Pageable page);

    Page<MstDepartment> findAllByDepartmentIsAutorizedTrueAndIsActiveTrueAndIsDeletedFalse(Pageable page);

    Page<MstDepartment> findAllByIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Pageable page);

    List<MstDepartment> findByDepartmentNameContains(String key);

    List<MstDepartment> findByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Long key);

    List<MstDepartment> findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Long key);

    List<MstDepartment> findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsMedicaldepartmentTrueOrderByDepartmentNameAsc(Long key);

    List<MstDepartment> findAllByDepartmentUnitIdUnitIdEqualsAndIsActiveTrueAndIsDeletedFalseAndIsMedicaldepartmentFalseOrderByDepartmentNameAsc(Long key);

    List<MstDepartment> findAllByDepartmentUnitIdUnitIdEqualsAndIsMedicaldepartmentTrueAndIsActiveTrueAndIsDeletedFalseOrderByDepartmentNameAsc(Long key);

    MstDepartment findByDepartmentNameEqualsAndIsActiveTrueAndIsDeletedFalse(String key);
}
            
