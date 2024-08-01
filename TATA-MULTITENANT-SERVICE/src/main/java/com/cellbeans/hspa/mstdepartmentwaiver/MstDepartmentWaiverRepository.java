package com.cellbeans.hspa.mstdepartmentwaiver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MstDepartmentWaiverRepository extends JpaRepository<MstDepartmentWaiver, Long> {

    Page<MstDepartmentWaiver> findByDwNameContainsAndIsActiveTrueAndIsDeletedFalse(String name, Pageable page);

    Page<MstDepartmentWaiver> findAllByIsActiveTrueAndIsDeletedFalse(Pageable page);

    List<MstDepartmentWaiver> findByDwNameContains(String key);

}
            
